package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRecordNotFoundException {

    @Test
    void testExceptionMessage() {
        String message = "Record not found";
        RecordNotFoundException exception = new RecordNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }
}
