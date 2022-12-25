package com.rs.tasklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class TasklistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasklistApplication.class, args);
	}

}

@Component
@RequiredArgsConstructor
class SampleDataInitializer {

	private final TaskRepository taskRepository;

	@EventListener(ApplicationEvent.class)
	public void initialize() {
		Flux<Task> tasks = Flux
			.just("Post on IG", "Write the blog", "Complete side project")
			.map(task -> new Task(null, task))
			.flatMap(taskRepository::save);


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
	private String id;

	private String name;
}
