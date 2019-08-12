package com.dominikpiotrowski.springrest.demo.api;

import com.dominikpiotrowski.springrest.demo.dao.Entity.dto.MovieDataTransfer;
import com.dominikpiotrowski.springrest.demo.services.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest extends AbstractRestControllerTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieController movieController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setControllerAdvice(new RestResponseEntityException())
                .build();
    }

    @Test
    public void findAll() throws Exception {
        MovieDataTransfer first = MovieDataTransfer.builder().title("Terminator").production(1984).maker("Cameron").build();
        MovieDataTransfer second = MovieDataTransfer.builder().title("Space Oddysey").production(1968).maker("Cubrick").build();

        when(movieService.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get(MovieController.BASE_URL + "/movies/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieDataTransfersList", hasSize(2)));
    }

    @Test
    public void getMovieById() throws Exception {
        MovieDataTransfer first = MovieDataTransfer.builder().title("Terminator").production(1984).maker("Cameron").build();

        when(movieService.getMovieById(anyLong())).thenReturn(first);

        mockMvc.perform(get(MovieController.BASE_URL + "/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Terminator")))
                .andExpect(jsonPath("$.production", equalTo(1984)))
                .andExpect(jsonPath("$.maker", equalTo("Cameron")));
    }

    @Test
    public void getMovieByTitle() throws Exception {
        MovieDataTransfer first = MovieDataTransfer.builder().title("Terminator").production(1984).maker("Cameron").build();

        when(movieService.getMovieByTitle(anyString())).thenReturn(first);

        mockMvc.perform(get(MovieController.BASE_URL + "/movieByTitle/Terminator")
                .param("title", first.getTitle()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.production", equalTo(1984)))
                .andExpect(jsonPath("$.maker", equalTo("Cameron")));
    }

   // TODO java.lang.AssertionError: JSON path "$.movieDataTransfersList"

    @Test
    public void getMovieByProduction() throws Exception {
        MovieDataTransfer first = MovieDataTransfer.builder().id(1L).title("Terminator").production(1984).maker("Cameron").build();

        when(movieService.getMovieByProduction(anyInt())).thenReturn(Arrays.asList(first));

        mockMvc.perform(get(MovieController.BASE_URL + "/moviesByProduction/1984")
                .param("production", String.valueOf(first.getProduction())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieDataTransfersList", hasSize(1)));
    }

    @Test
    public void getMovieByMaker() throws Exception{
        MovieDataTransfer first = MovieDataTransfer.builder().id(1L).title("Terminator").production(1984).maker("Cameron").build();
        List<MovieDataTransfer> movies = Arrays.asList(first);

        when(movieService.getMovieByMaker(anyString())).thenReturn(movies);

        mockMvc.perform(get(MovieController.BASE_URL + "/moviesByMaker/Cameron")
                .param("maker", first.getMaker()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieDataTransfersList", hasSize(1)));
    }

    @Test
    public void deleteById() throws Exception {
        MovieDataTransfer first = MovieDataTransfer.builder().id(1L).title("Terminator").production(1984).maker("Cameron").build();

        movieService.deleteById(first.getId());
        mockMvc.perform(get(MovieController.BASE_URL + "/movies/1"))
                .andExpect(status().isOk());

        verify(movieService, times(1)).deleteById(first.getId());
    }

    @Test
    public void addMovie() throws Exception {
        MovieDataTransfer movie = MovieDataTransfer.builder().id(1L).title("Terminator").production(1984).maker("Cameron").build();
        when(movieService.addMovie(any(MovieDataTransfer.class))).thenReturn(movie);

        mockMvc.perform(post(MovieController.BASE_URL + "/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().isCreated());
    }

    //TODO No value at JSON path "$.title"

    @Test
    public void updateMovie() throws Exception {
        MovieDataTransfer movie = MovieDataTransfer.builder().id(1L).title("Terminator").production(1984).maker("Cameron").build();
        MovieDataTransfer afterUpdate = MovieDataTransfer.builder().id(1L).title("Space Oddysey").production(1968).maker("Cubrick").build();

        when(movieService.updateMovie(anyLong(), eq(movie))).thenReturn(afterUpdate);

        mockMvc.perform(put(MovieController.BASE_URL + "/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(afterUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Space Oddysey")));
    }

    //TODO No value at JSON path "$.title"

    @Test
    public void patchMovie() throws Exception {
        MovieDataTransfer movie = MovieDataTransfer.builder().id(1L).title("Terminator").production(1984).maker("Cameron").build();
        MovieDataTransfer patchedMovie = MovieDataTransfer.builder().id(1L).title("Space Oddysey").production(1968).maker("Cubrick").build();

        when(movieService.patchMovie(anyLong(), eq(movie))).thenReturn(patchedMovie);

        mockMvc.perform(post(MovieController.BASE_URL + "/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(patchedMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Space Oddysey")));
    }
}