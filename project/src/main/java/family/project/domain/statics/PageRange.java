package family.project.domain.statics;

import lombok.Data;
import lombok.Setter;

public class PageRange {
    public static int startCul(int currentPage) {
        return (currentPage % 10 == 0) ? (10 * ((currentPage / 10) - 1)) + 1 : 10 * (currentPage / 10) + 1;

    }
    public static int endCul(int currentPage,int totalPage) {
        int end = (currentPage % 10 == 0) ? 10 * (((currentPage / 10) - 1) + 1) : 10 * ((currentPage / 10) + 1);
        return Math.min(end, totalPage);
    }
}

