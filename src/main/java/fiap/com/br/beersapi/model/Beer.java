package fiap.com.br.beersapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Cerveja cadastrada na API")
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @Schema(example = "Heineken")
    private String name;

    @Schema(example = "Lager")
    private String style;

    @ManyToOne
    @JoinColumn(name = "brewery_id", nullable = false)
    @Schema(description = "Cervejaria responsável pela cerveja")
    private Brewery brewery;

    @Schema(description = "Teor alcoólico em porcentagem", example = "5.0")
    private Double abv;

    @Schema(description = "Índice de amargor (IBU)", example = "23")
    private Integer ibu;

}
