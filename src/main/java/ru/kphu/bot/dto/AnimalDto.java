package ru.kphu.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.model.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AnimalDto {

    private int id;
    private String name;
    private String jpg;
    private int status;
    private String idUser;
    private List<Answer> answers;

    public static AnimalDto from(Animal animal){
        return AnimalDto.builder()
                .id(animal.getId())
                .name(animal.getName())
                .status(animal.getStatus())
                .jpg(animal.getJpg())
                .idUser(animal.getIdUser())
                .answers(new ArrayList<Answer>())
                .build();
    }

    public static List<AnimalDto> from (List<Animal> animals){
        return animals.stream()
                .map(AnimalDto::from)
                .collect(Collectors.toList());
    }
}
