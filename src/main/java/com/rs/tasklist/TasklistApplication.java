package com.rs.tasklist;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class TasklistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasklistApplication.class, args);
	}

}

@RestController
@RequiredArgsConstructor
class TaskRestController {
	private final TaskRepository taskRepository;

	@GetMapping("/tasks")
	Publisher<Task> getTasks() {
		return this.taskRepository.findAll();
	}

	@PostMapping("/tasks")
	Mono<Void> saveTask(@RequestBody Task task) {
		return this.taskRepository.save(task).then();
	}
}

@Component
@RequiredArgsConstructor
@Slf4j
class SampleDataInitializer {

	private final TaskRepository taskRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void initialize() {
		var tasks = Flux
			.just("Post on IG", "Write the blog", "Complete side project")
			.map(task -> new Task(null, task))
			.flatMap(taskRepository::save);

		taskRepository
			.deleteAll()
			.thenMany(tasks)
			.thenMany(taskRepository.findAll())
			.subscribe(new Consumer<Task>() {
				@Override
				public void accept(Task task) {
					log.info("task : {}", task);
				}
			});
	}
}

interface TaskRepository extends ReactiveCrudRepository<Task, String> {

}

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
class Task {

	@Id
	@JsonSerialize(using= ToStringSerializer.class)
	private String id;
	private String name;
}
