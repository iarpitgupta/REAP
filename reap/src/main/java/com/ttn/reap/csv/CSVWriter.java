package com.ttn.reap.csv;

import com.ttn.reap.entities.Post;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVWriter {

    public static void writePostToCSV(PrintWriter writer, List<Post> postsList){

        try {
            CSVPrinter printer = new CSVPrinter
                    (writer, CSVFormat.DEFAULT.withHeader
                            ("ID","Sender's Name","Receiver's Name","Badge","Comment","Date of creation"));
            for (Post post: postsList) {
                List<String> data = Arrays.asList(
                        post.getId().toString(),
                        post.getSender().getFullName().toString(),
                        post.getReceiver().getFullName().toString(),
                        post.getBadge().toString(),
                        post.getComment().toString(),
                        post.getDateOfCreation().toString()
                );
                printer.printRecord(data);

            }
        } catch (IOException e) {
            System.out.println("CSV writer error!!");
            e.printStackTrace();
        }
    }
}
