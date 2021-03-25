package de.unistuttgart.t2;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeFooIT extends FooTest {

    // Execute the same tests but in native mode.
}