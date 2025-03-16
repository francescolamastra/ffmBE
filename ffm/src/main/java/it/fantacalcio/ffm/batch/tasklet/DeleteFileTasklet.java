package it.fantacalcio.ffm.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DeleteFileTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String filePath = (String) chunkContext.getStepContext().getJobParameters().get("filePath");
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("File deleted: " + filePath);
        } else {
            System.out.println("File not found: " + filePath);
        }
        return RepeatStatus.FINISHED;
    }
}
