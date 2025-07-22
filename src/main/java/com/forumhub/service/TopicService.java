package com.forumhub.service;

import com.forumhub.dto.TopicRequestDTO;
import com.forumhub.dto.TopicResponseDTO;
import com.forumhub.entity.Course;
import com.forumhub.entity.Topic;
import com.forumhub.entity.User;
import com.forumhub.repository.CourseRepository;
import com.forumhub.repository.TopicRepository;
import com.forumhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public TopicResponseDTO createTopic(TopicRequestDTO dto) {
        Optional<User> authorOpt = userRepository.findById(dto.getAuthorId());
        Optional<Course> courseOpt = courseRepository.findById(dto.getCourseId());

        if (authorOpt.isEmpty() || courseOpt.isEmpty()) {
            throw new RuntimeException("Autor ou curso não encontrado");
        }

        Topic topic = new Topic();
        topic.setTitle(dto.getTitle());
        topic.setMessage(dto.getMessage());
        topic.setAuthor(authorOpt.get());
        topic.setCourse(courseOpt.get());

        topicRepository.save(topic);
        return new TopicResponseDTO(topic);
    }

    public List<TopicResponseDTO> getAllTopics() {
        return topicRepository.findAll()
                .stream()
                .map(TopicResponseDTO::new)
                .collect(Collectors.toList());
    }

    public TopicResponseDTO getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
        return new TopicResponseDTO(topic);
    }

    public TopicResponseDTO updateTopic(Long id, TopicRequestDTO dto) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        topic.setTitle(dto.getTitle());
        topic.setMessage(dto.getMessage());
        topicRepository.save(topic);

        return new TopicResponseDTO(topic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
