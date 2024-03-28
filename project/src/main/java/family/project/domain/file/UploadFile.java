package family.project.domain.file;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class UploadFile {
    //화면은 고객이 업로드한 파일명인 uploadFileName을 사용해서 업로드를 하고, 서버 내부에서 실질적으로 관리하는 파일명은 storeFileName으로 지정하자.(현재 이미지 저장 경로: C:/projectimg/)
    private String uploadFileName;//고객이 업로드한 파일명 -> 서로 다른 고객이 같은 파일 이름을 업로드 하는 경우 서버 내부에 저장된 기존 파일 이름과 충돌 발생 그래서 서버 내부 관리 파일명 필요
    private String storeFileName;//서버 내부에서 관리하는 파일명 uuid

    protected UploadFile(){};



    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
