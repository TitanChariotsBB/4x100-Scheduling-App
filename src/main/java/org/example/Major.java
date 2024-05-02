package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Major {
    private String major;
    private int year;
    private ArrayList<String> requirements;

    public Major(String major, int year) {
        this.major = major;
        this.year = year;
        loadRequirements();
    }

    public String requirementsToString() {
        String out = "";
        for(String word : requirements) {
            out = out + word;
        }
        return out;
    }
    private void loadRequirements() {
        String text = "";
        if (year == 2023) {
            try {
                String l = major.replace(" ","");
                String s = "https://my.gcc.edu/docs/registrar/programguides/statussheets/2023/" + l + "_" + (year+4) + ".pdf";
                URL url = new URL(s);
                PDDocument document = PDDocument.load(url.openStream());

                PDFTextStripper pdfStripper = new PDFTextStripper();
                pdfStripper.setStartPage(1);
                pdfStripper.setEndPage(1);
                text = pdfStripper.getText(document);

                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //System.out.println(text);

        Scanner scnr = new Scanner(text);
        while(scnr.hasNext()) {
            String s = scnr.next();
            if(s.length() == 4) {
                Boolean isCapital = true;
                for (int i = 0; i < 4; i++) {
                    if(!Character.isUpperCase(s.charAt(i))) {
                        isCapital = false;
                        break;
                    }
                }
                if(isCapital) {
                    requirements.add(s + scnr.next());
                }
            }
        }
    }

    public static void main(String[] args) {
        Major cs = new Major("Computer Science BS", 2023);
        System.out.println(cs.requirementsToString());

    }

}

