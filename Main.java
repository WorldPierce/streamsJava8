package com.company;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) {

//        Thread th = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("A thread!");
//            }
//        });

        Thread th = new Thread(() -> System.out.println("A thread!"));
        th.start();

        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        //external iterator (using sequential iteration)
//        for(int e : numbers) {
//            System.out.println(e);
//        }

        //internal iterator (benefits from polymorphism
        numbers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });
        //lambda of previous example
        numbers.forEach((Integer value) -> System.out.println(value));
        //Do not need Integer, the type is inferred with lambda, only need to omit () when its 1 arg
        numbers.forEach(value -> System.out.println(value));

        //replace lambda with METHOD REFERNCE - only used for passing an argument through
        //calling print on the System.outE object
        numbers.forEach(System.out::print);

        numbers.stream()
                //.map(e -> String.valueOf(e))
                //equivalent to
                .map(String::valueOf)
                .forEach(System.out::println);

//        numbers.stream()
//                .map(e -> String.valueOf(e))
//                //.map(e -> e.toString())
//                .map(String::toString)
//                .forEach(System.out::println);
        System.out.println(
            numbers.stream()
                    //order recived must be the same as order used
                    //.reduce(0, (total, e) -> Integer.sum(total , e))
                    //equivalent too
                    .reduce(0, Integer::sum)

        );

        System.out.println(
                numbers.stream()
                        .map(String::valueOf)
                        //.reduce("", (carry, str) -> carry.concat(str))
                        .reduce("", String::concat)
        );
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //given the values, double the even numbers and total.
        int result = 0;
        for(int e : numbers) {
            if(e % 2 == 0) {
                result += e * 2;
            }
        }
        System.out.println(result);

        //parallelStream can save a lot of time, but uses a lot of threads and resources.
        numbers.parallelStream()
        // numbers.stream()
                .filter(Main::isEven)
                //.map(Main::doubleValue)
                //.reduce(0, (carry, e) -> carry + e)
                //.reduce(0, Integer::sum);
                .mapToInt(Main::doubleValue)
                .sum();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //filter
        //input: Stream<T> filter takes Predicate<T>

        //map transforms values
        //number of output == number if input
        //no garuntee on output type with respect to input type
        //inout Stream<T> map takes Function<T, R> to return Stream<R>

        //both filter and map stay within their swimlanes(only works with 1 certain value at a time, nothing before our after)

        //reduce cuts across swimlanes
        //reduce on Stream<t> takes 2 parameters:
        //1st of type T
        //2nd is of type BiFunction<R, T, R> to produce results of type R
        //can also call sum on maps to int/doubles/etc

        numbers = Arrays.asList(1,2,3,4,5,1,2,3,4,5);

        //double the even values and put into list
        //NEVER do shares mutability, instead do this!

        List<Integer> doubleOfEvens =
                numbers.stream()
                .filter(Main::isEven)
                .map(e -> e * 2)
                .collect(Collectors.toList());

        //collect is another reduce function, can also us toSet() to avoid duplicates


        List<Person> people = createPeople();

        //create a Map with name and age as key, and person as value
        System.out.println(
                people.stream()
                      .collect(toMap(
                              person -> person.getName() + "-" + person.getAge(),
                              person -> person)));

        //given a list of people, create a map where name is key and value is all people with that name
        System.out.println(
                people.stream()
                      .collect(groupingBy(Person::getName)));

        //ma,e key and all ages of people with name as value
        System.out.println(
                people.stream()
                        .collect(groupingBy(Person::getName,
                                mapping(Person::getAge, toList()))));



        //sort and make numbers distinct in lists
        //properties of lists: sized, ordered, non-distinct, non-sorted

        numbers.stream()
                .filter(e -> e % 2 ==0)
                .sorted()
                .distinct()
                .forEach(System.out::println);


        System.out.println(
        Stream.iterate(0, e -> e + 1) //unbounded lazy
                .filter(e -> e % 2 == 0) //unbounded lazy
                .filter(e -> Math.sqrt(e) > 20) //unbounded lazy
                .mapToInt(e -> e * 2) //unbounded lazy
                .limit(10) //sized, lazy
                .sum());

    }

    public static List<Person> createPeople() {
        return Arrays.asList(
                new Person("Sara", Gender.FEMALE, 20),
                new Person("Sara", Gender.FEMALE, 22),
                new Person("Bob", Gender.MALE, 20),
                new Person("Paula", Gender.FEMALE, 32),
                new Person("Paul", Gender.MALE, 30),
                new Person("Jack", Gender.MALE, 20),
                new Person("Jack", Gender.FEMALE, 70),
                new Person("Jill", Gender.FEMALE, 12)


        );
    }

    private static int doubleValue(Integer integer) {
        return integer * 2;
    }

    public static boolean isEven(int e) {
        return e % 2 == 0;
    }





}
