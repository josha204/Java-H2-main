package Testing;

import game.*;
import java.util.*;

public class learnStacks {
    public static void main(String[] args) {
        exampleStack();
    }

    // Example Stack    -   output: 4321
    public static void exampleStack() {
        // Create a new stack
        Stack<Integer> stack = new Stack<>();

        // Push elements onto the stack
        for (int i = 0; i < 4; i++) {
            stack.push(i);
            System.out.println("\nThe element at the top of the stack is " + stack.peek());
            
        }

        // Pop elements from the stack
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
    }
}
