# rx-java-quiz
[![Build Status](https://travis-ci.org/yuriykulikov/rx-java-quiz.svg?branch=master)](https://travis-ci.org/yuriykulikov/rx-java-quiz)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
## Quiz
You task is to implement the solutions for Koans. A Koan is an **abstract** test, a test which has all the test code, but
the actual logic is not implemented. Your task is to implement this logic.

### Howto
* Look for a class ending with "Koan"
* Extend this class
* Try running it as a JUnit test
* Try making the tests green
* Repeat no more Koans left

### Documentation
Let's say we implement a SomeKoanSolution for SomeKoan.
Use Ctrl+Q in IntelliJ or JavaDoc view in Eclipse to see the documentation of the *Koan class. For example:
```java
public class SomeKoanSolution extends SomeKoan /* <- for documentation select here and click Ctrl+Q!*/ {
  @Override
  Observable<Integer> koanMethod() {
    // implementation
  }
}
```

### Execution
Run the implementation class as a JUnit test (Two arrow signs near the class name). For example:
```java
/* Start tests here using two arrows sign! >> */ public class SomeKoanSolution extends SomeKoan {
  @Override
  Observable<Integer> koanMethod() {
    // implementation
  }
}
```

## Types of koans
There are beginner, intermediate and pro koans.
### Beginner
Beginner koans require knowledge about 1-2 basic operators or factory methods. Tasks are straightforward.

### Intermediate
Intermediate require better knowledge of more complicated operators or may require to use a combination of multiple
operators. Tasks are relatively easy to understand, and if not, there are detailed error messages. You may have to look
in the test source code.

### Pro
Pro koans can be quite demanding. They are either hard to understand or hard to implement. You may need more time
for some of these koans. Some require profound knowledge of RxJava operators.

### Order
It is better to start with beginner tasks, then finish all intermediate tasks and then (maybe) continue to pro koans.

## Results
### Self-education
If you are using this for self-education, try to implement everything (eventually).