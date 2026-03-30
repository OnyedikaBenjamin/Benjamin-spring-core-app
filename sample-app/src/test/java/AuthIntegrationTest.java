import org.junit.jupiter.api.Test;
import org.security.SampleApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHealth() throws Exception {
        mockMvc.perform(get("/api/public/health"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegisterUser() throws Exception {

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "username":"john",
                          "password":"password"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/user/me"))
                .andExpect(status().isUnauthorized());
    }
}