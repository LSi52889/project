package com.example.lovepreet300352889.web;

import com.example.lovepreet300352889.entities.salesman;
import com.example.lovepreet300352889.repositories.salesmanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.web.servlet.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;





@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class salesmanControllerTest {
    salesman salesman;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    salesmanRepository salesmanRepository;

    @Mock
    View mockView;

    @InjectMocks
    salesmanController salesmanController;

    @BeforeEach
    void setup() throws ParseException {
        salesman = new salesman();
        salesman.setId(Long.valueOf(23));
        salesman.setName("Lovepreet");
        salesman.setItem("Washing Machine");
        salesman.setAmount(1000.0);
        String sDate1 = "2012/12/09";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        salesman.setTdate(date1);

        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(salesmanController).setSingleView(mockView).build();
    }

    @Test
    public void findAll_ListView() throws Exception {
        List<salesman> list = new ArrayList<salesman>();
        list.add(salesman);
        list.add(salesman);

        when(salesmanRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listSalesman", list))
                .andExpect(view().name("salesmans"))
                .andExpect(model().attribute("listSalesman", hasSize(2)));

        verify(salesmanRepository, times(1)).findAll();
        verifyNoMoreInteractions(salesmanRepository);
    }

    @Test
    void delete() {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(salesmanRepository).deleteById(idCapture.capture());
        salesmanRepository.deleteById(1L);
        assertEquals(1L, idCapture.getValue());
        verify(salesmanRepository, times(1)).deleteById(1L);
    }

    @Test
    void save() {
        when(salesmanRepository.save(salesman)).thenReturn(salesman);
        salesmanRepository.save(salesman);
        verify(salesmanRepository, times(1)).save(salesman);
    }

    @Test
    void editCustomer() throws Exception {
        salesman s2 = new salesman();
        s2.setId(Long.valueOf(23));
        s2.setName("Lovepreet");
        s2.setItem("Washing Machine");
        s2.setAmount(1000.0);
        String sDate1 = "2012/12/09";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        s2.setTdate(date1);

        Long iid =Long.valueOf(23);
        when(salesmanRepository.findById(iid)).thenReturn(Optional.of(s2));
        mockMvc.perform(get("/editSalesman").param("id", String.valueOf(Long.valueOf(23))))
                .andExpect(status().isOk())
                .andExpect(model().attribute("salesman", s2))
                .andExpect(view().name("editSalesman"));

        verify(salesmanRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(salesmanRepository);
    }
}