package com.liyijun.ladder;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class LadderControllerTest {

    @Test
    public void lowercase() {
        String input = new String("Work");
        LadderController l = new LadderController();
        String output = l.lowercase(input);
        assertEquals("work", output);
    }

    @Test
    public void findLadder1() {
        HashSet<String> dic = new HashSet<String>();
        try {
            FileReader infile1 = new FileReader("../resources/static/dictionary.txt");
            String line = "";
            BufferedReader infile2 = new BufferedReader(infile1);
            while ((line = infile2.readLine()) != null) {
                dic.add(line);
            }
        } catch (FileNotFoundException ex) {
            return;
        } catch (IOException e) {
            return;
        }
        LadderController testLadder = new LadderController();
        Stack<String> empty = new Stack<String>();
        Stack<String> ladderFound = testLadder.findLadder(dic, "code", "data", 4);
        Stack<String> ans = new Stack<String>();
        ans.push("code");
        ans.push("cade");
        ans.push("cate");
        ans.push("date");
        ans.push("data");

        assertEquals(ans, ladderFound);
    }

    @Test
    public void findLadder2() {
        HashSet<String> dic = new HashSet<String>();
        try {
            FileReader infile1 = new FileReader("../resources/static/dictionary.txt");
            String line = "";
            BufferedReader infile2 = new BufferedReader(infile1);
            while ((line = infile2.readLine()) != null) {
                dic.add(line);
            }
        } catch (FileNotFoundException ex) {
            return;
        } catch (IOException e) {
            return;
        }
        LadderController testLadder = new LadderController();
        Stack<String> empty = new Stack<String>();
        Stack<String> ladderFound = testLadder.findLadder(dic, "cat", "dog", 3);
        Stack<String> ans = new Stack<String>();
        ans.push("cat");
        ans.push("cot");
        ans.push("dot");
        ans.push("dog");

        assertEquals(ans, ladderFound);
    }


}