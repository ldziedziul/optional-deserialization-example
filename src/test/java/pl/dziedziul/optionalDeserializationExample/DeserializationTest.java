package pl.dziedziul.optionalDeserializationExample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

    @Test
    public void shouldDeserializeClassWithOptionalFieldWrappedWithAnotherOne() throws JsonProcessingException {
        OuterResponse outerResponse = mapper.readValue("{\"transferResponse\":" +
                        "{\n" +
                        "  \"id\": \"some-id\"\n" +
                        "}" +
                "}", OuterResponse.class);
        assertThat(outerResponse.getTransferResponse().getMaybeString()).isEmpty();
    }
}


@Value
//@NoArgsConstructor(force = true,access = AccessLevel.PRIVATE)
class GetTransferResponse {
    private final String id;
    private final Optional<String> maybeString;
}

@Value
//@NoArgsConstructor(force = true,access = AccessLevel.PRIVATE)
class OuterResponse {
    private final GetTransferResponse transferResponse;
}
