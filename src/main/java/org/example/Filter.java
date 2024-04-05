package org.example;

import java.time.LocalDateTime;

public class Filter {
    public Search.SearchBy sb;
    public String filter;
    public LocalDateTime[][] meetings;

    public Filter(Search.SearchBy sb, String filter) {
        this.sb = sb;
        this.filter = filter;
    }

    public Filter(Search.SearchBy sb, LocalDateTime[][] meetings) {
        this.sb = sb;
        this.meetings = meetings;
    }
}
