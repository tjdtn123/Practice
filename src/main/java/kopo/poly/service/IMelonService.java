package kopo.poly.service;

import kopo.poly.dto.MelonDTO;

import java.util.List;
import java.util.Map;

public interface IMelonService {

    int collectMelonSong() throws Exception;

    List<MelonDTO> getSongList() throws Exception;

    List<MelonDTO> getSingerSongCnt() throws Exception;

    List<MelonDTO> getSingerSong() throws Exception;

    int collectMelonSongMany() throws Exception;


}
