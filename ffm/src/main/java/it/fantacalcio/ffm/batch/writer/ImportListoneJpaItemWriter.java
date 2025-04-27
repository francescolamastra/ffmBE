package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.converter.GiocatoreConverter;
import it.fantacalcio.ffm.converter.GiocatoreListoneConverter;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.domain.entity.GiocatoreListone;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

@RequiredArgsConstructor
public class ImportListoneJpaItemWriter implements ItemWriter<GiocatoreListoneGiocatoreComposite> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final JpaItemWriter<Giocatore> giocatoreItemWriter;
    private final JpaItemWriter<GiocatoreListone> listoneItemWriter;

    @Override
    public void write(Chunk<? extends GiocatoreListoneGiocatoreComposite> chunk) {
        for (GiocatoreListoneGiocatoreComposite wrapper : chunk) {
            if(!apiGatewayFacade.giocatoreExistsByIdFantagazzetta(wrapper.getGiocatoreDto().idFantagazzetta())){
                giocatoreItemWriter.write(Chunk.of(GiocatoreConverter.toEntity(wrapper.getGiocatoreDto())));
            }
            if(!apiGatewayFacade.giocatoreListoneExistsByStagioneAndTipologiaListoneAndIdFantagazzetta(wrapper.getGiocatoreListoneDto().idFantagazzetta(), wrapper.getGiocatoreListoneDto().idStagione(), wrapper.getGiocatoreListoneDto().tipologiaListone())){
                listoneItemWriter.write(Chunk.of(GiocatoreListoneConverter.toEntity(wrapper.getGiocatoreListoneDto())));
            }
        }
    }
}
