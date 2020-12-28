package day16;

import common.BaseInputReader;
import day16.model.CodedTicket;
import day16.model.Interval;
import day16.model.TicketInfo;
import day16.model.TicketRules;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputReader extends BaseInputReader<TicketRules> {

    private final Pattern intervalsPattern = Pattern.compile("\\h?([0-9]*)-([0-9]*)(\\hor\\h([0-9]*)-([0-9]*)!)*");

    public TicketRules read(URI filePath) {
        TicketRules rules = null;
        try (var scanner = new Scanner(new File(filePath))) {
            var ticketInfos = new ArrayList<TicketInfo>();
            CodedTicket currentCodedTicket = null;
            var neighbourTickets = new ArrayList<CodedTicket>();
            var section = 0;
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                if (line.isEmpty()) {
                    section++;
                    line = scanner.nextLine();
                }
                switch (section) {
                    case 0 -> {
                        var labelAndIntervals = line.split(":");
                        var label = labelAndIntervals[0];
                        var intervalsStr = labelAndIntervals[1];
                        var matcher = intervalsPattern.matcher(intervalsStr);
                        var intervals = new ArrayList<Interval>();
                        while (matcher.find()) {
                            var start = Integer.parseInt(matcher.group(1));
                            var end = Integer.parseInt(matcher.group(2));
                            intervals.add(new Interval(start, end));
                        }

                        ticketInfos.add(new TicketInfo(label, intervals));
                    }
                    case 1, 2 -> {
                        if ("your ticket:".equals(line) || "nearby tickets:".equals(line)) {
                            line = scanner.nextLine();
                        }

                        var currentTicketParams = Arrays.stream(line.split(","))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());

                        if (section == 1) {
                            currentCodedTicket = new CodedTicket(currentTicketParams);
                        } else {
                            neighbourTickets.add(new CodedTicket(currentTicketParams));

                        }
                    }
                }
            }

            rules = new TicketRules(ticketInfos, currentCodedTicket, neighbourTickets);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return rules;
    }
}
