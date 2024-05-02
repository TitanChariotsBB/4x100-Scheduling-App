package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Major {
    private String major;
    private ArrayList<String> requirements;

    public Major(String major) {
        this.major = major;
        requirements = new ArrayList<>();
        loadRequirements();
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public String requirementsToString() {
        String out = "";
        for(String word : requirements) {
            out = out + word + "\n";
        }
        return out;
    }
    private void loadRequirements() {

        String text = "";
        try {
            String l = major.replace(" ","");
            String s = "https://my.gcc.edu/docs/registrar/programguides/statussheets/2023/" + l + "_2027.pdf";
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
                    String s2 = scnr.next();
                    if(s2.matches("[0-9]+"))
                    requirements.add(s + s2);
                }
            }
        }
    }

//    public static void main(String[] args) {
//        Major cs = new Major("Computer Science BS");
//        System.out.println(cs.requirementsToString());
//
//    }

}
