package main.java.ua.lviv.iot.regex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringProcessor {

  public Map<String, Integer> repeatingWords = new HashMap<>();
  public String text;
  public int numberOfTimes;
  
  public StringProcessor() {
    super();
  }
  
  public StringProcessor(String text, int numberOfTimes) {
    this();
    this.text = text;
    this.numberOfTimes = numberOfTimes;
  }

  public static String readInputText() throws IOException {
    String out = "";
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.print("Enter Text:\n");
      out += scanner.nextLine();
      System.out.print("Enter Number of times:\n");
      out += scanner.nextInt();
      return out;
    }
  }
  
  public void showResults() {
    if (repeatingWords.size() == 0) {
      System.out.println("No words repeating more than "+numberOfTimes+" times");
    } else {
      System.out.println("Words that repeating more than "+numberOfTimes+" times:\n");
      for (String word : repeatingWords.keySet()) {
        System.out.println(word);
      }
    }
  }

  public static void main(String... args) throws IOException {
    String text = readInputText();
    final int numberOfTimes = Integer.parseInt(text.substring(text.length() - 1));
    text = text.substring(0,text.length() - 1);
    final StringProcessor proc = new StringProcessor(text, numberOfTimes);
    proc.findWordsRepeatingMoreTimesThan();
    proc.showResults();
  }
  
  public String findWordsRepeatingMoreTimesThan() {
    final Pattern pattern = Pattern.compile("\\p{L}+");
    final Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      int res;
      final String word  = matcher.group();
      if (repeatingWords.get(word) == null) {
        repeatingWords.put(word, 1);
      } else {
        res = repeatingWords.get(word);
        repeatingWords.replace(word,res + 1);
      }
    }
    repeatingWords = 
        repeatingWords.entrySet().stream()
        .filter(x -> x.getValue() > numberOfTimes)
        .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
    return repeatingWords.entrySet().stream()
      .map(x -> x.getKey() + ' ')
      .collect(Collectors.joining());
  }
  
  public String findWordsRepeatingMoreTimesThan(final String text, final int numberOfTimes) {
    StringProcessor proc = new StringProcessor(text, numberOfTimes);
    return proc.findWordsRepeatingMoreTimesThan();
  }
  
}