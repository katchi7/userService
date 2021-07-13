package ma.tna.ebanking.userservice.dtos;

import ma.tna.ebanking.userservice.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    public LanguageDto(Language language){
        this(language.getLanguageIso639_1(),language.getLanguageNativeName());
    }
    private String languageIso639_1;
    private String languageNativeName;
    public Language asLanguage(){
        return new Language(languageIso639_1,languageNativeName);
    }
}