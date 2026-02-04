package com.fnmc.backend.dao;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class CompsDao {
    private String player;
    private String named;
    private List compData;
    //private  String comData2;

    private List<List<Long>> blst;
    private List<String> compTxt;
    private List<String> dtxt;
}
