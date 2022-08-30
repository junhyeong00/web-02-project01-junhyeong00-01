package utils;

import models.Post;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostLoaderTest {
    @Test
    void loadPost() throws FileNotFoundException {
        PostLoader postLoader = new PostLoader();

        List<Post> posts = postLoader.loadPost();

        assertNotNull(posts);
    }

    @Test
    void parsePost() {
        PostLoader postLoader = new PostLoader();

        Post post = postLoader.parsePost("1,제목,내용,토끼,1,디지털기기,false");

        assertEquals("제목 - 내용", post.toString());
    }
}
