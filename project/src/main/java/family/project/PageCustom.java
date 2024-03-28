package family.project;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCustom {

    private Integer page;
    private PageRequest pageRequest;


    public PageCustom(Integer size,Integer page) {
        if (page == null) {
            this.page = 1;
        } else
            this.page = page;
        this.pageRequest = PageRequest.of(this.page - 1, size);
    }
}
