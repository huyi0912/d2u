import org.reactivestreams.FlowAdapters;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LearnReactor {
    public static void main(String[] args){
        learnSubscribe();
        if(true) return;
        Flux.range(1,5).log()
                .map(i->i*2)
                .take(3)
                //.interval(Duration.ofMillis(100))
                .subscribe(System.out::println);
        Mono.firstWithValue(
                Mono.just(1).map(x->"foo"+x),
                Mono.delay(Duration.ofMillis(1)).thenReturn("bar")
                ).log()
                .subscribe(System.out::println);
        Mono.empty().log().subscribe(System.out::println);

        Mono.never().log().subscribe(System.out::println);

        Mono.error(new IllegalStateException("wrong")).onErrorComplete();
System.out.println("--------------");

        Flux<String> f = Flux.just("foo","bar").log();
        f.subscribe(System.out::println);
        StepVerifier.create(f).expectNext("foo").expectNext("bar").verifyComplete();
        Flux<User> flux = null;

        StepVerifier sv = StepVerifier.withVirtualTime(()->flux.take(4))
                .thenRequest(3).expectNextCount(4).expectComplete().verifyLater();

        StepVerifier sv1 = StepVerifier.withVirtualTime(()->flux.take(4))
                .thenRequest(3).expectNext(User.SKYLER)
                .thenRequest(3).expectNext(User.JESSE).thenCancel();

        flux.doFirst(()->System.out.println("Starring"))
                .doOnNext(u->System.out.println(u.getFirstname()+" "+u.getLastname()))
                .doOnComplete(()->System.out.println("The ends!"));

        Mono.just(User.SAUL).onErrorReturn(User.JESSE);
        flux.onErrorResume(t->Flux.just(User.WALTER));
        flux.map(new Function<User, Object>() {
            public Object apply(User u){
                return null;
            }
        });
        Flux<String> usernameFlux = Flux.just("JL");
        Flux<String> firstnameFlux = Flux.just("John");
        Flux<String> lastnameFlux = Flux.just("Lee");
            List<Flux<String>> a  = new java.util.ArrayList<Flux<String>>();
            a.add(usernameFlux);
            a.add(firstnameFlux);
            a.add(lastnameFlux);
            Flux.zip(a,p->new User((String)p[0],(String)p[1],(String)p[2]));
        //List<String> a = Collections.("1","2","3");


        Flux.just("").doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        });
//        Flux.defer(new Supplier<Publisher<? extends Object>>() {
//            @Override
//            public Publisher<? extends Object> get() {
//                return null;
//            }
//        }).subscribeOn()
        //Mono.justOrEmpty(Optional.ofNullable())
//        Tuple3 a;
//        Flux.zip()
//        FlowAdpters.toFlowPublisher()
        flux.map((u)->{
                try{
                    return new User("1","2","3");
                }catch(Exception e) {
                    throw new RuntimeException("");
                }});
                //(e,u)->Flux<User>.just(User.SAUL,User.JESSE));
    }

    static void learnSubscribe() {
        Flux<Integer> ints = Flux.range(1,4)
                .map(i->{
                    if(i<=4) return i;
                    else throw new RuntimeException("Got to 4");
                });
        Disposable subscriber = ints.subscribe(System.out::println,System.err::println,()->System.out.println("done"));
        BaseSubscriber a;
    }
    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        return flux.flatMap(Flux::just);
//        return flux.flatMap(new Function<User, Publisher<User>> {
//            public Publisher<User> apply (User u){
//                return Flux<User>.just(u);
//            }
//        })
    }

    public static class User {

        public static final User SKYLER = new User("swhite", "Skyler", "White");
        public static final User JESSE = new User("jpinkman", "Jesse", "Pinkman");
        public static final User WALTER = new User("wwhite", "Walter", "White");
        public static final User SAUL = new User("sgoodman", "Saul", "Goodman");

        private final String username;

        private final String firstname;

        private final String lastname;

        public User(String username, String firstname, String lastname) {
            this.username = username;
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public String getUsername() {
            return username;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            User user = (User) o;

            if (!username.equals(user.username)) {
                return false;
            }
            if (!firstname.equals(user.firstname)) {
                return false;
            }
            return lastname.equals(user.lastname);

        }

        @Override
        public int hashCode() {
            int result = username.hashCode();
            result = 31 * result + firstname.hashCode();
            result = 31 * result + lastname.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "username='" + username + '\'' +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    '}';
        }
    }
}
