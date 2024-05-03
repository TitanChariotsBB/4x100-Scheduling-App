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
    private enum majors {
        ACCOUNTING,
        FINANCE,
        PHILOSOPHY,
        CHRISTIANMINISTRIES,
        BIOLOGY,
        BIOLOGYHEALTH,
        CONSERVATIONBIOLOGY,
        MOLECULARBIOLOGY,
        BIOCHEMISTRY,
        CHEMISTRY,
        BIOCHEMISTRY_HEALTH,
        COMMUNICATIONARTS,
        DESIGNANDINNOVATION,
        COMPUTERSCIENCEBA,
        COMPUTERSCIENCEBS,
        DATASCIENCE,
        ECONOMICS,
        BUSINESSECONOMICS,
        ELEMENTARYEDUC,
        ELECTRICALENGINEERING,
        COMPUTERENGINEERING,
        ENGLISH,
        ENTREPRENEURSHIP,
        EXERCISESCIENCE,
        HISTORY,
        BUSINESSANALYSIS,
        BUSINESSSTATISTICS,
        HUMANRESOURCEMANAGEMENT,
        INTERNATIONALBUSINESS,
        MANAGEMENT,
        MARKETING,
        SUPPLYCHAINMANAGEMENT,
        MATHEMATICS,
        APPLIEDSTATISTICS,
        MECHANICALENGINEERING,
        FRENCH,
        SPANISH,
        MUSIC,
        MUSICBUSINESS,
        NURSING,
        PHYSICS,
        POLITICALSCIENCE,
        PYSCHOLOGYBA,
        PSYCHOLOGYBS,
        SOCIOLOGY;
    }


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
        while(scnr.hasNext()){
            if(scnr.nextLine().startsWith("General Education")) {
                break;
            }
        }

        //System.out.println(text);


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

    public void loadRequirements(String n) {
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
        while(scnr.hasNext()){
            if(scnr.nextLine().startsWith("General Education")) {
                break;
            }
        }
        while(scnr.hasNextLine()) {
            String line = scnr.nextLine();
            if(hasCourse(line)) {

            }
        }
    }

    public Boolean hasCourse(String s) {
        Scanner sc = new Scanner(s);
        while(sc.hasNext()) {
            if(s.length() == 4) {
                Boolean isCapital = true;
                for (int i = 0; i < 4; i++) {
                    if(!Character.isUpperCase(s.charAt(i))) {
                        isCapital = false;
                        break;
                    }
                }
                if(isCapital) {
                    String s2 = sc.next();
                    if(s2.matches("[0-9]+"))
                        return true;
                }
            }
        }
        return false;
    }

    public static int countSimilarChars(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        int count = 0;

        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        for (majors m : majors.values()){
            System.out.println(m.name());
        }
        Major major;
        Scanner sc = new Scanner(System.in);
        System.out.println("Type your major:");
        String m = sc.next();
        for (majors s : majors.values())
            if(s.name().equalsIgnoreCase(m)) {
                major = new Major(m);
                System.out.println(major.requirementsToString());
                return;
            }

//        int currScore = 0;
//        String currMatch = "";
//        for (majors s : majors.values()) {
//            String newS = s.name();
//            if (countSimilarChars(m, newS) > currScore) {
//                currScore = countSimilarChars(m, newS);
//                currMatch = newS;
//            }
//        }
//        System.out.println("Did you mean " + currMatch + " (Y/n)?");
//        String next = sc.next();
//        if (next.equalsIgnoreCase("y")) {
//            major = new Major(currMatch);
//        }
    }

}
