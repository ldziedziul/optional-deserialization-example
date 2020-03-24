package pl.dziedziul.optionalDeserializationExample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class DeserializationTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        GetTransferResponse getTransferResponse = mapper.readValue("{}", GetTransferResponse.class);
        assertThat(getTransferResponse.getMaybeString()).isEmpty();
    }

    @Test
    public void shouldDeserializeWithOptionalFilled() throws JsonProcessingException {
        GetTransferResponse getTransferResponse = mapper.readValue("{\n" +
                "  \"id\": \"some-id\",\n" +
                "  \"maybeString\": \"some-string\"\n" +
                "}", GetTransferResponse.class);
        assertThat(getTransferResponse.getMaybeString()).hasValue("some-string");
    }
}


@Value
class GetTransferResponse {
    private final String id;
    private final Optional<String> maybeString;
}