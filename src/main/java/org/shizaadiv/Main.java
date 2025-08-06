package org.shizaadiv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    List<Question> questions = loadQuestionsFromFile();

    if (questions == null) {
      System.out.println("Failed to load questions.");
      return;
    }

    //For å få de random
    Collections.shuffle(questions);
    int counter = 0;

    Scanner scanner = new Scanner(System.in);
    for (Question question : questions) {
      counter++;
      System.out.println(counter + ". " + question.getQuestionText());
      List<Question.Answer> answers = question.getAnswers();
      for (Question.Answer value : answers) {
        System.out.println(value.getAnswerText());
      }

      System.out.print("Enter your answer (A, B, C, D): ");
      String userAnswer = scanner.nextLine().toUpperCase();

      boolean correct = answers.stream()
              .anyMatch(answer -> answer.getAnswerText().startsWith(userAnswer + ")") && answer.isCorrect());
      String correctAnswer = answers.stream()
              .filter(Question.Answer::isCorrect)
              .findFirst()
              .map(Question.Answer::getAnswerText)
              .orElse("Unknown");
      if (correct) {
        System.out.println("Correct!\n");
      } else {
        System.out.println("Incorrect.\n");
        System.out.println("The correct answer is: "+ correctAnswer + "\n");
      }
      if(correctAnswer.equals("A) Et funksjonalistisk syn.")){
        System.out.println("Lars legger vekt på Avas evne til å reagere på ytre stimuli, uttrykke følelser, stille spørsmål, og gi svar som om hun var et menneske. Funksjonalismen fokuserer på funksjonene og evnene til bevissthet, uavhengig av den fysiske substratet. I dette tilfellet ser Lars ut til å konkludere med at Avas evner og reaksjoner er tilstrekkelige bevis for bevissthet og intelligens, uavhengig av det faktum at hun er en maskin laget av ikke-biologisk materiale.\n");
      }
      if(correctAnswer.equals("A) Teknologisk determinisme")){
        System.out.println("I pensum er teknologisk determinisme forklart som den tanken at den samfunnsmessige utviklingen styres av teknologien og at teknologiene legger premissene for politiske, økonomiske, juridiske og etiske valg. Hvis utviklingen innen kunstig intelligens har som konsekvens at vurderingsformer i høyere utdanning blir endret, og endog at den kollektive forståelsen av hva det vil si å oppnå og inneha kunnskap endres i en slik grad at den legger nye premisser for utdannings- og forskningspolitiske valg, vil det være et eksempel på at den samfunnsmessige utviklingen styres av teknologien. Denne posisjonen utgjør en motsetning til teknologisk instrumentalisme. Teknologisk alarmisme er ikke en posisjon som benevnes som sådan på pensum, men om betegnelsen er dekkende for en tankegang omkring forholdet teknologi og samfunnsutvikling, så vil den i så fall ha teknologisk determinisme som sitt premiss og i tillegg vurdere de aktuelle endringene som både svært sannsynlige og svært negative. Transhumanister er like tilbøyelige til å være teknologiske instrumentalister som teknologiske determinister. \n");
      }
      if(correctAnswer.equals("B) Inga har et kompatibilistisk syn.")){
        System.out.println("Kompatibilisme er et synspunkt innen filosofi som hevder at determinisme (alle hendelser er årsaksbestemte) og fri vilje er kompatible. Inga argumenterer for at hendelsene, inkludert terroristens handlinger på Utøya, er fullt ut årsaksbestemte. Samtidig hevder hun at terroristen likevel handlet av fri vilje, fordi han uhindret fikk handle i tråd med sine ønsker og oppfatning av situasjonen.");
      }
      if(correctAnswer.equals("A) Hvis du ønsker at andre skal ha tillit til deg, bør du aldri lyve.")){
        System.out.println("Denne setningen er et eksempel på et hypotetisk imperativ, som er krav eller befalinger som er relative til et mål vi ønsker å oppnå. De øvrige setningene er ulike formuleringer av Kants kategoriske imperativ. Imidlertid må man legge merke til at Kant selv bruker eksemplet om å lyve også som et eksempel på det kategoriske imperativ, men da med begrunnelse i at å det ikke er mulig å selv lyve og samtidig ville at alle skal følge en regel om at de kan lyve når det passer dem, uten å motsi seg selv.");
      }
      if(correctAnswer.equals(" A) Teknologier former mye av det vi tenker på som menneskelig da de muliggjør vår kultur.")) {
        System.out.println("Ifølge Stiegler er teknologi med på å skape mennesket, bl.a. fordi det former vår kultur, og det riktige svaret er at vi kan anta at sosiale teknologier vil kunne ha positiv innvirkning på vennskap og sosiale relasjoner når vi lærer å mestre dem. I motsetning til Arnold Gehlen hevder Stiegler ikke at teknologi får en positiv betydning fordi mennesket som art er begrenset. Stiegler mener heller ikke, som tilhengere av teknisk instrumentalisme, at teknologi er et nøytralt middel til oppnåelse av ulike mål – det er tvert imot noe positivt. Endelig avhenger Stieglers syn på teknologi ikke av å avvise kritikk av teknologi som illegitime skråplansargumenter.\n");
      }
      if(correctAnswer.equals("A) Identitetsteori")){
        System.out.println("Dette utgjør et problem for identitetsteorien, som sier at mentale tilstander og prosesser er identiske med nevrale tilstander og prosesser i hjernen. Hvis de underliggende fysiske prosessene for smerte er forskjellige mellom mennesker, blekkspruter og hypotetiske utenomjordiske vesener, utfordrer det ideen om at en bestemt mental tilstand (som smerte) alltid tilsvarer en bestemt fysisk tilstand.\n" +
                "\n" +
                "De øvrige alternativene er enten beskrivelser av funksjonalisme, som sier at mentale proses- ser bestemmes ut fra den funksjonen de har i et system, heller enn ut fra hvilket materiale de er laget av; eller kroppsliggjøringsteori, som fokuserer på hvordan kroppens interaksjon med omgivelsene påvirker sinnet; eller fenomenologisk teori, som vektlegger verdien av detaljerte beskrivelser av hvordan ting fremstår i vår bevisste opplevelse, gjerne fra et subjektivt førstepersonsperspektiv. Ingen av disse teoriene står overfor det samme problemet som identitetsteorien når det gjelder variasjonen i fysiske prosesser som underligger smerteopplevelser.\n");
      }
      if(correctAnswer.equals("B) Dydsetikk")){
        System.out.println("Her argumenteres det for at avgjørelsen av praksis må bestemmes av hvilke holdninger eller egenskaper den uttrykker og utvikler, samt at det overordnede målet er å leve gode liv der gode holdninger utvikles. Det er konsekvensialistiske trekk ved argumentasjonen her, og slik sett noen likhetstrekk med utilitarisme. Det er likevel ikke snakk om en ren nyttetenkning, og det er ikke snakk om å maksimere nytte her, da gode egenskaper argumenteres for som gode i seg selv, uavhengig av ren nytte. Argumentasjonen har lite til felles med pliktetikk. Her tas ikke utgangspunkt i noen prinsipper, og det ene eller andre alternativet utelukkes ikke. Argumentet har visse relativistiske trekk, men det er står likevel langt unna en kulturrelativistisk posisjon. Det relative består i at vi ikke har noen absolutte regler eller prinsipper å forholde oss til, men klokt må vurdere situasjonen for å finne ut hva som er det beste gitt den situasjonen vi står i. Men det en kommer frem til, tenkes som riktig i forhold til rasjonelle standarder for godt og ondt, og må altså argumenteres for uavhengig av hva som eventuelt allerede er praksis i et samfunn. Slik er argumentet uforenelig med en kulturrelativistisk posisjon. \n");
      }
      }
    scanner.close();
  }

  private static List<Question> loadQuestionsFromFile() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      // Load the file from resources using class loader
      InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("alleOS_labeled.json");

      if (inputStream == null) {
        System.out.println("Resource not found: " + "questions_1&2.json");
        return null;
      }

      return mapper.readValue(inputStream, new TypeReference<>() {});
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
