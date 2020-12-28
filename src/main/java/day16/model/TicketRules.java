package day16.model;

import java.util.List;
import java.util.stream.Collectors;

public record TicketRules(List<TicketInfo>ticketInfos, CodedTicket currentCodedTicket, List<CodedTicket>nearbyCodedTickets) {

    public List<Interval> getAllIntervalsFlat() {
        return this.ticketInfos()
                .stream()
                .flatMap(infos -> infos.intervals().stream())
                .collect(Collectors.toList());
    }

    public List<CodedTicket> getValidNearbyTickets(List<Interval> allIntervals) {
        return this
                .nearbyCodedTickets()
                .stream()
                .filter(codedTicket -> codedTicket.isValid(allIntervals))
                .collect(Collectors.toList());
    }
}
