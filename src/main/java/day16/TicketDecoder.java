package day16;

import day16.model.CodedTicket;
import day16.model.IndexLabelMapping;
import day16.model.Ticket;

import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicketDecoder implements BiFunction<CodedTicket, IndexLabelMapping, Ticket> {

    @Override
    public Ticket apply(CodedTicket codedTicket, IndexLabelMapping indexLabelMapping) {
        var codedTicketParams = codedTicket.parameters();
        var labelToValue = IntStream.range(0, codedTicketParams.size())
                .boxed()
                .collect(Collectors.toMap(indexLabelMapping::getLabelForIndex, codedTicketParams::get));

        return new Ticket(labelToValue);
    }
}
