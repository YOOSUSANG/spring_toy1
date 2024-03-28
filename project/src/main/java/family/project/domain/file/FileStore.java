package family.project.domain.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {


    @Value("${file.dir}")
    private String fileDir;

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        if (multipartFiles == null) {
            return null;
        }
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        //form으로 전송된게 없으면 즉 파일이나 첨부파일을 업로드 하지 않았으면 null로 보낸다.
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); //업로드 파일명
        //서버 내부에 저장 되는 파일 이름 uuid.png , uuid.jpg 형식으로 저장할 것이다.
        //a.png 라는 이름으로 업로드하면 51041c62-86e4-4274-801d-614a7d994edb.png 와 같이 저장한다.
        String storeFileName = createStoreFileName(originalFilename);

        //***** 파일을 저장하는 부분이다.***** getFullPath(storeFileName)라는 이름으로 multipartFile 내용으로 파일을 저장한다.
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf("."); // 확장자 명을 떄기 위해서 . 위치가 pos이다.
        return originalFilename.substring(pos + 1); // . 이후 문자들 모두 가져오기 (확장자 명)
    }


}
