package com.liyijun.ladder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@RestController
public class LadderController {
    private static final Logger logger = LogManager.getFormatterLogger("log printer");
    public static String lowercase(String str){
        char[] chars = str.toCharArray();
        int len = str.length();
        for(int i = 0; i<len; i++){
            char c = chars[i];
            if(Character.isUpperCase(c)) {
                chars[i] = Character.toLowerCase(c);
            }
        }
        String str_new = new String(chars);
        return str_new;
    }

    public static Stack<String> findLadder(HashSet dic, String begin, String end, int len){
        // words already appeared in stacks
        HashSet<String> wordpool = new HashSet<String>();
        wordpool.add(begin);
        // the original word
        Stack<String> s1 = new Stack<String>();
        Queue<Stack<String>> wordladder = new LinkedList<Stack<String>>();
        s1.push(begin);
        wordladder.offer(s1);

        while (!wordladder.isEmpty()){
            for(int i = 0;i<len;i++){
                for(int j=97;j<=122; j++){
                    Stack<String> cur_stack = wordladder.peek();
                    String cur_word = cur_stack.peek();
                    String neighbor = cur_word;
                    neighbor = neighbor.substring(0,i)+(char)j+neighbor.substring(i+1);
                    if((!wordpool.contains(neighbor)) && (dic.contains(neighbor))) {
                        wordpool.add(neighbor);
                        if (end.equals(neighbor)) {
                            //successfully find a path
                            cur_stack.push(neighbor);
                            return cur_stack;
                        }
                        else {
                            Stack<String> s = (Stack<String>)cur_stack.clone();
                            s.push(neighbor);
                            wordladder.offer(s);
                        }
                    }

                }

            }
            wordladder.poll();
        }
        s1.pop();
        return s1;
    }

    @RequestMapping("/")
    public String index() {
        return "Please login";
    }
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(value = "/wl")
    @ResponseBody
    public String main(String path, String begin_input, String end_input) throws IOException {
        logger.info("Start!");

        StringBuilder res = new StringBuilder();
        HashSet<String> dic = new HashSet<String>();
        FileReader infile1 = null;
        try{
            // create a file object with filename
            path="src/main/resources/static/"+path;
            infile1 = new FileReader(path);
        }catch(FileNotFoundException ex){ return (path); }
        String line = "";
        BufferedReader infile2 = new BufferedReader(infile1);
        while((line = infile2.readLine()) != null){ dic.add(line); }
        infile1.close();
        infile2.close();

        boolean input_stat = true;

            String begin = begin_input;
            String end = end_input;
                if(begin.length()<=0){
                    input_stat = false;
                }

                if(end.length()<=0){
                    input_stat = false;

                }
                // check existence
                if(!dic.contains(begin)|| !dic.contains(end)){ return ("The two words must be found in the dictionary.\n"); }
                else if (begin.equals(end)){ return ("The two words must be different.\n"); }
                else if (begin.length() != end.length()){ return ("The two words must be the same length.\n"); }

            if(!input_stat) return ("Have a nice day\n");
            int len = begin.length();
            Stack<String> result = findLadder(dic, begin, end, len);
            if (result.isEmpty()) {
                res = new StringBuilder("No word ladder found from " + end + " back to " + begin + ".\n");
                logger.info("No ladder.");
            }
            else {
                res.append("A ladder from ").append(end).append(" to ").append(begin).append(":\n");
                logger.info("Ladder exists.");
                while (!result.isEmpty()) {
                    res.append(result.peek());
                    result.pop();
                    res.append("\n");
                }
                return String.valueOf(res);
            }
        return "Have a nice day.\n";
    }

}
