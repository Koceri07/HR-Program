package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.mapper.ApplicantCvMapper;
import com.hrprogram.hrprogram.model.request.ApplicantCvRequest;
import com.hrprogram.hrprogram.repository.ApplicantCvRepository;
import com.hrprogram.hrprogram.repository.CvRepository;
import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicantCvService {

    private final ApplicantCvRepository applicantCvRepository;
    private final CvRepository cvRepository;


    @Value("${email.username}")
    private String emailUserName;

    @Value("${email.password}")
    private String emailPassword;

    @Value("${email.imap.host}")
    private String emailHost;


    @Value("${email.save-dir}")
    private String saveDir;



    public void saveCvFile(ApplicantCvRequest applicantCvRequest) {
        log.info("Action.saveCvFile.start for id {}", applicantCvRequest.getId());

        try {
            Path path = Path.of(applicantCvRequest.getFilePath());
            byte[] fileBytes = Files.readAllBytes(path);

            String fileHash = DigestUtils.sha256Hex(fileBytes);

            if (applicantCvRepository.existsByFileHash(fileHash)) {
                log.warn("Duplicate CV detected (hash: {}). Skipping save.", fileHash);
                return;
            }

            var cvEntity = ApplicantCvMapper.INSTANCE.toEntity(applicantCvRequest);
            cvEntity.setFileHash(fileHash);
            applicantCvRepository.save(cvEntity);

            log.info("Action.saveCvFile.end for id {}", applicantCvRequest.getId());

        } catch (IOException e) {
            log.error("Error reading file from path: {}", applicantCvRequest.getFilePath(), e);
        }
    }


    @Scheduled(fixedDelay = 30000)
    public void checkEmailForNewCvs() {
        log.info("Action.checkEmailForNewCvs.start");

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.imap.host", emailHost);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imap");
            store.connect(emailHost, emailUserName, emailPassword);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            log.info("Found {} unread messages", messages.length);

            for (Message message : messages) {
                log.info("Processing message: Subject='{}', From='{}'", message.getSubject(), message.getFrom()[0]);

                if (message.getContent() instanceof Multipart multipart) {
                    log.info("Multipart count: {}", multipart.getCount());

                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        String disposition = bodyPart.getDisposition();
                        String fileName = bodyPart.getFileName();

                        log.info("BodyPart[{}] disposition: {}, fileName: {}", i, disposition, fileName);

                        if (Part.ATTACHMENT.equalsIgnoreCase(disposition)
                                && fileName != null
                                && fileName.toLowerCase().endsWith(".pdf")) {

                            String uniqueFileName = UUID.randomUUID() + "_" + fileName;

                            InputStream is = bodyPart.getInputStream();
                            byte[] fileBytes = IOUtils.toByteArray(is);

                            String fileHash = DigestUtils.sha256Hex(fileBytes);

                            if (applicantCvRepository.existsByFileHash(fileHash)) {
                                log.warn("Duplicate CV detected: {}", fileName);
                                continue;
                            }

                            // Faylı yaddaşa yaz, uğursuzdursa növbəti fayla keç
                            if (!saveFileToDisk(saveDir, uniqueFileName, fileBytes)) {
                                continue;
                            }

                            ApplicantCvRequest request = new ApplicantCvRequest();
                            request.setId(null);
                            request.setHrId(1L);
                            request.setFilePath(saveDir + File.separator + uniqueFileName);
                            request.setOriginalFileName(fileName);

                            var entity = ApplicantCvMapper.INSTANCE.toEntity(request);
                            entity.setFileHash(fileHash);
//                            applicantCvRepository.save(entity);
                            var cvEntity = ApplicantCvMapper.INSTANCE.applicantRequestToCvEntity(request);
                            cvRepository.save(cvEntity);


                            log.info("Saved new CV: {}", fileName);
                        }
                    }
                }

                message.setFlag(Flags.Flag.SEEN, true);
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            log.error("Action.checkEmailForNewCvs.error", e);
        }

        log.info("Action.checkEmailForNewCvs.end");
    }


    private boolean saveFileToDisk(String directory, String fileName, byte[] content) {
        File targetFile = new File(directory + File.separator + fileName);

        File parentDir = targetFile.getParentFile();
        if (!parentDir.exists()) {
            boolean dirCreated = parentDir.mkdirs();
            if (dirCreated) {
                log.info("Created directory: {}", parentDir.getAbsolutePath());
            } else {
                log.error("Failed to create directory: {}", parentDir.getAbsolutePath());
                return false;
            }
        }

        try {
            Files.write(targetFile.toPath(), content);
            log.info("Saved file to disk: {}", targetFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            log.error("Failed to write file: {}", targetFile.getAbsolutePath(), e);
            return false;
        }
    }



    public List<ApplicantCvRequest> getAllCvs(){
        log.info("Action.getAllCvs.start");
        var cvs = applicantCvRepository.findAll().stream()
                .map(ApplicantCvMapper.INSTANCE::toRequest)
                .toList();
        log.info("Action.getAllCvs.end");
        return cvs;
    }

}
