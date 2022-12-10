package com.example.studentpractice.web;

import com.example.studentpractice.entities.Customer;
import com.example.studentpractice.repositories.CustomerRepository;
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
class CustomerControllerTest {
    Customer customer;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    View mockView;

    @InjectMocks
    CustomerController customerController;

    @BeforeEach
    void setup() throws ParseException {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("lovepreet");
        customer.setSeatno("1A");
        String sDate1 = "2012/12/09";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        customer.setTdate(date1);

        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(customerController).setSingleView(mockView).build();
    }

    @Test
    public void findAll_ListView() throws Exception {
        List<Customer> list = new ArrayList<Customer>();
        list.add(customer);
        list.add(customer);

        when(customerRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listCustomer", list))
                .andExpect(view().name("customers"))
                .andExpect(model().attribute("listCustomer", hasSize(2)));

        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void delete() {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(customerRepository).deleteById(idCapture.capture());
        customerRepository.deleteById(1L);
        assertEquals(1L, idCapture.getValue());
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void save() {
        when(customerRepository.save(customer)).thenReturn(customer);
        customerRepository.save(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void editCustomer() throws Exception {
        Customer s2 = new Customer();
        s2.setId(1L);
        s2.setName("lovepreet");
        s2.setSeatno("1D");
        String sDate1 = "2012/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        s2.setTdate(date1);

        Long iid = 1l;
        when(customerRepository.findById(iid)).thenReturn(Optional.of(s2));
        mockMvc.perform(get("/editCustomer").param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customer", s2))
                .andExpect(view().name("editCustomer"));

        verify(customerRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(customerRepository);
    }
}