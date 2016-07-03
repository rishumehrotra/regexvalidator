/**
 *
 */

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author rishumehrotra
 */
public class Validator {

    public static final String pattern = "";


    public static void main(String[] args) throws Exception {

        // check if there are any arguments passed
        if (args.length == 0) {
            System.out.println("There is no input files provided....Kindly provide some as :");
            System.out.println("java -jar fileReader.jar directory_name");
            System.exit(0);
        }

        // now read files for expression formation
        String expression = formExpression(args[0]);
        // now validate the expression for correctness
        Boolean isValid = patternVerifier(expression);

        System.out.println(isValid);
    }

    @SuppressWarnings("deprecation")
    protected static String readFile(String filename) throws Exception {

        try {

            System.out.println("Trying to read data from " + filename);
            String data = FileUtils.readFileToString(new File(filename));
            // trim the data to remove any extra spaces etc
            return data.trim();
        } catch (FileNotFoundException fne) {
            System.out.println(fne);
            System.out.println("There was no file found for name " + filename + ". Will be returning blank");
            return "";
        }
    }

    protected static String formExpression(String directoryName) throws Exception {

        StringBuilder expressionBuilder = new StringBuilder();

        for (String s : getFilesInDirectory(directoryName)) {
            expressionBuilder.append(readFile(s));
        }

        return expressionBuilder.toString();
    }

    private static ArrayList<String> getFilesInDirectory(String directoryName) throws Exception {

        ArrayList<String> names = new ArrayList<>();

        if (isDirectory(directoryName)) {
            // get all the fileNames from this in sorted by filename
            Collection<File> filesList = FileUtils.listFiles(new File(directoryName), null, true);
            for (File f : filesList) {
                names.add(f.getAbsolutePath());
            }
        } else {
            names.add(new File(directoryName).getAbsolutePath());
        }

        Collections.sort(names); // sorting on absolute path

        return names;

    }

    private static Boolean isDirectory(String directoryName) throws Exception {

        File f = new File(directoryName);
        if (f.isDirectory())
            return true;
        return false;
    }

    protected static Boolean patternVerifier(String input) throws Exception {

        // first check if the number of opening and closing parantheses are same
        if (StringUtils.countMatches(input, "(") != StringUtils.countMatches(input, ")")) {
            System.out.println("The parantheses do not match in number! Please check!");
            return false;
        }

        // now to check if they occur correctly
        if (!isParanthesesCorrect(input)) {
            System.out.println("Parantheses are not correctly closed! Please check!");
            return false;
        }

        // now to check for other cases
        if (!isOtherConditionMet(input)) {
            System.out.println("Basic conditions not being met! Please check!");
            return false;
        }

        return true;
    }

    private static Boolean isOtherConditionMet(String input) throws Exception {

        char[] split = input.toCharArray();

        String symbols = "+-/*";

        // no symbol and ( should be together
        for (Integer i=0;i<split.length;i++) {
            if (Character.isLetter(split[i])) {
                // ensure that next is an operator or ) or end of string
                if (i != split.length - 1) // which means it's not the last
                // character
                {
                    if (symbols.indexOf(split[i + 1]) < 0 && split[i + 1] != ')') {
                        System.out.println("Oops ! Looks like an operand is followed by another operand");
                        return false;
                    }
                }
            }

            if (split[i] == ')' || split[i] == '(') {
                continue;
            }

            // Similarly; if character is an operator; it should have another (
            // or operand after it
            Integer index = symbols.indexOf(split[i]);
            if (index == -1) {
                continue;
            } else {
                if (i == split.length - 1) {
                    return false;
                }
                if (split[i + 1] != '(' && !Character.isLetter(split[i + 1])) {
                    System.out.println("Oops ! Looks like an operator is followed by another operator or invalid curls");
                    return false;
                }
            }

//            //if the character is ) and in the middle of string; it should always be followed by a symbol
//            if(c == ')')
//            {
//                index = ArrayUtils.indexOf(split,c);
//                if(index!=split.length-1)
//                {
//                    //now just state that the next term should be an operator or another )
//                    if(symbols.indexOf(split[index+1])<0 && split[index+1]!=')')
//                    {
//                        return false;
//                    }
//                }
//            }
//
//            //if the character is ( and in the middle of string then it should always be followed by a literal
//            if(c == '(')
//            {
//                index = ArrayUtils.indexOf(split,c);
//                if(index==split.length-1) {return false;}
//                {
//                    //now just state that the next term should be a literal or another )
//                    if(!Character.isLetter(split[index+1]) && split[index+1]!=')')
//                    {
//                        return false;
//                    }
//                }
//            }
        }

        return true;
    }

    private static Boolean isParanthesesCorrect(String input) throws Exception {

        // check if the correct number of parantheses are there in an order
        Integer paranCounter = 0;
        for (Character c : input.toCharArray()) {
            if (c == '(') {
                paranCounter++;
            } else if (c == ')') {
                paranCounter--;
            }
            if (paranCounter < 0) {
                System.out.println("Not possible ever! since you cannot have a ) before (");
                return false;
            }
        }

        if (paranCounter == 0) {
            return true;
        }
        return false;

    }

}
