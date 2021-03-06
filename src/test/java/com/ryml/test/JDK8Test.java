package com.ryml.test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ryml.entity.Student;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * description:JDK1.8新特性测试类
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/7/1
 */
public class JDK8Test {
    private static List<Student> students;

    static {
        Student student = new Student();
        student.setAge(1);
        student.setName("小明");
        Student student1 = new Student();
        student1.setAge(10);
        student1.setName("小李");
        Student student2 = new Student();
        student2.setAge(20);
        student2.setName("小红");
        Student student3 = new Student();
        student3.setAge(30);
        student3.setName("小兰");
        Student student4 = new Student();
        student4.setAge(40);
        student4.setName("柯南");
        students = Arrays.asList(student, student1, student2, student3, student4);
    }

    @Test
    public void testStream() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> stream = integers.stream();
        List<Integer> collect = stream.filter(JDK8Test::add).collect(Collectors.toList());
        System.out.println(collect);
    }

    public static boolean add(Integer i) {
        return i == 1;
    }

    public static void main(String[] args) {
        students.stream().map(s -> s.getMyTest().equals("测试"));
    }

    public static <T> List<T> test(Predicate<T> predicate, List<T> list) {
        List<T> result = new ArrayList<T>();
        list.forEach(element -> {
            if (predicate.test(element)) {
                result.add(element);
            }
        });
        return result;
    }

    @Test
    public void test() {
        Runnable r = () -> {
        };
        A a = () -> {
        };
    }


    @Test
    public void test1() {
        List<Object> transform = Lists.transform(students, new com.google.common.base.Function<Student, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable Student student) {
                return 1;
            }
        });
        System.out.println(transform);
    }

    public static String disposeSqlForWhereCondition(JSONObject params) {
        System.out.println(params);
        return null;
    }

    public static <T, R> R test(T params, Function<T, R> function) {
        return function.apply(params);
    }

    @Test
    public void test2() {
        students.parallelStream().filter(s -> {
            System.out.println(s.getName() + "----");
            return s.getAge() >= 3;
        }).map(s -> {
            System.out.println(s.getName() + "====");
            return s.getName();
        }).collect(Collectors.toList());

       /* students.parallelStream().forEach(s->System.out.println(s.getName()+"==="));
        for /(Student student5 : students) {
            System.out.println(student.getName()+"----");
        }*/
    }

    @Test
    public void testLimitAndSkip() {
        List<Student> collect = students.stream().skip(2).limit(1).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void testMapFlatMap() {
        String[] strs = new String[]{"hello", "world"};
        Stream<String> stream = Arrays.stream(strs);
        List<String> collect = stream.map(s -> s.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println(collect);

        Integer[] is = new Integer[]{1, 2, 3};
        Integer[] is2 = new Integer[]{4, 5, 6};

        Stream<Integer> stream1 = Arrays.stream(is);
        List<Integer> collect1 = stream1.map(i -> i * i).collect(Collectors.toList());
        System.out.println(collect1);
        Stream<Integer> stream2 = Arrays.stream(is2);

        Object[] collect2 = stream2.flatMap(i2 -> {
            Stream<Integer> stream3 = Arrays.stream(is);
            return stream3.map(i1 -> new Integer[]{i1, i2});
        }).toArray();
        for (Object o : collect2) {
            Integer[] i3 = (Integer[]) o;
            for (Integer integer : i3) {
                System.out.print(integer + ",");
            }
            System.out.println();
        }
    }

    @Test
    public void testReduce() {
        List<Integer> is = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        is.stream().reduce(Integer::min).ifPresent(System.out::println);

        is.stream().reduce(Integer::max).ifPresent(System.out::println);

        is.stream().reduce(Integer::sum).ifPresent(System.out::println);

        Integer reduce = is.stream().reduce(0, Integer::sum);

        System.out.println(reduce);
    }

    @Test
    public void testRange() {
        IntStream.rangeClosed(1, 100).boxed().
                flatMap(a -> IntStream.rangeClosed(a, 100).
                        filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).
                        mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                ).limit(5).forEach(c -> System.out.println(c[0]+","+c[1]+","+c[2]));
    }

    @Test
    public void testStreamIterate(){
        Stream.iterate(new int[]{0,1},n->new int[]{n[1],n[0]+n[1]}).limit(10).map(n->n[0]).forEach(System.out::println);
    }

    @Test
    public void testD() throws IOException {
        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/location/ip?ip=111.199.191.251&ak=tlmoYKcoazSg0dmp9G14DpKW4IutyfUz&coor=bd09ll");
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        String gbk = new String(result.getBytes("gbk"), StandardCharsets.UTF_8);
        System.out.println(gbk);
    }
}

interface A {
    Integer id = 0;

    default void test() {

    }

    default void test1() {

    }

    void test3();
}

abstract class B {
    Integer id;

    abstract void test();
}

