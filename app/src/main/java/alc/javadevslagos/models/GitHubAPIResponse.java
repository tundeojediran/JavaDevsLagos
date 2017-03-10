package alc.javadevslagos.models;

import java.util.List;

/**
 * Created by dannytee on 10/03/2017.
 */

public class GitHubAPIResponse {

    private int total_count;
    private boolean incomplete_results;
    private List<JavaDeveloper> javaDevelopers = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public GitHubAPIResponse() {
    }

    public GitHubAPIResponse(int total_count, boolean incomplete_results, List<JavaDeveloper> javaDevelopers) {
        super();
        this.total_count = total_count;
        this.incomplete_results = incomplete_results;
        this.javaDevelopers = javaDevelopers;
    }

    public int gettotal_count() {
        return total_count;
    }

    public void settotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isincomplete_results() {
        return incomplete_results;
    }

    public void setincomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<JavaDeveloper> getItems() {
        return javaDevelopers;
    }

    public void setItems(List<JavaDeveloper> javaDevelopers) {
        this.javaDevelopers = javaDevelopers;
    }

}
