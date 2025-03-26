package it.fantacalcio.ffm.batch.tasklet;

import it.fantacalcio.ffm.batch.utility.FileManager;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class DeleteFileTasklet implements Tasklet {

    private final FileManager fileManager;

    public DeleteFileTasklet(FileManager fileManager){
        this.fileManager = fileManager;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String filePath = (String) chunkContext.getStepContext().getJobParameters().get("filePath");
        fileManager.deleteFile(filePath);
        return RepeatStatus.FINISHED;
    }
}
