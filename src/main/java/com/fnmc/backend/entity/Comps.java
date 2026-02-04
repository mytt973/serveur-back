package com.fnmc.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comps {
    @Id
    private String id;

    private String player;

    private String named;
    private List<List<Long>> blst;
    private List compData;
    //private String compData2;

    private List<String> dtxt;
    private List<String> compTxt;


    private Boolean isPrivate=true;



}
