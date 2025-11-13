package com.oliver.portfolio.datagen;

import com.oliver.portfolio.enums.Role;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.SkillProgress;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.SkillProgressRepository;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
@Order(1)
public class UserSkillDataGenerator implements CommandLineRunner {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(UserSkillDataGenerator.class);
  
  private final UserRepository userRepository;
  private final SkillRepository skillRepository;
  private final PasswordEncoder passwordEncoder;
  private final SkillProgressRepository skillProgressRepository;
  private static final Random random = new Random();
  
  public UserSkillDataGenerator(UserRepository userRepository,
                                SkillRepository skillRepository,
                                PasswordEncoder passwordEncoder,
                                SkillProgressRepository skillProgressRepository) {
    this.userRepository = userRepository;
    this.skillRepository = skillRepository;
    this.passwordEncoder = passwordEncoder;
    this.skillProgressRepository = skillProgressRepository;
  }
  
  private static final List<String> USERNAMES = List.of(
      "oliver.dev",
      "sara.codes",
      "ethan.js",
      "mia.designs",
      "liam.tech",
      "ava.data",
      "noah.backend",
      "emma.frontend",
      "lucas.devops",
      "sophia.qa"
  );
  
  private static final List<Pair<String, String>> SKILLS = List.of(
      Pair.of("Java", "Strong understanding of OOP and enterprise development."),
      Pair.of("Spring Boot", "Building REST APIs and microservices using Spring Boot."),
      Pair.of("Hibernate", "Experience with ORM and JPA for database access."),
      Pair.of("React", "Developing dynamic and reusable front-end components."),
      Pair.of("TypeScript", "Writing type-safe, maintainable frontend code."),
      Pair.of("JavaScript", "Deep understanding of ES6+ and browser APIs."),
      Pair.of("HTML5", "Structuring accessible and semantic web pages."),
      Pair.of("CSS3", "Styling responsive and modern UI layouts."),
      Pair.of("Tailwind CSS", "Building consistent and scalable UI components quickly."),
      Pair.of("Node.js", "Creating backend services and APIs using Express."),
      Pair.of("Express.js", "REST API development and middleware configuration."),
      Pair.of("PostgreSQL", "Writing efficient SQL queries and managing schemas."),
      Pair.of("MySQL", "Database design and query optimization."),
      Pair.of("MongoDB", "Designing NoSQL data models and aggregation pipelines."),
      Pair.of("Docker", "Containerizing applications for portability and scaling."),
      Pair.of("Kubernetes", "Managing and deploying containerized services."),
      Pair.of("Git", "Version control and collaborative workflows."),
      Pair.of("GitHub Actions", "Automating CI/CD pipelines."),
      Pair.of("Jenkins", "Continuous integration and automated build pipelines."),
      Pair.of("AWS EC2", "Deploying and scaling cloud-based applications."),
      Pair.of("AWS S3", "Managing static assets and cloud storage."),
      Pair.of("AWS Lambda", "Building serverless event-driven functions."),
      Pair.of("Azure DevOps", "CI/CD and infrastructure management on Azure."),
      Pair.of("Google Cloud", "Deploying applications using GCP services."),
      Pair.of("REST API Design", "Structuring scalable and maintainable API endpoints."),
      Pair.of("JSON", "Working with structured data and API communication."),
      Pair.of("JWT Authentication", "Implementing secure token-based authentication."),
      Pair.of("OAuth2", "Integrating third-party authentication providers."),
      Pair.of("WebSockets", "Enabling real-time communication in apps."),
      Pair.of("Redux", "Managing complex frontend state effectively."),
      Pair.of("Zustand", "Lightweight state management for React apps."),
      Pair.of("Recoil", "Modern React state management library."),
      Pair.of("Jest", "Writing unit and integration tests for JS/TS."),
      Pair.of("JUnit", "Testing backend Java components."),
      Pair.of("Mockito", "Mocking and verifying interactions in tests."),
      Pair.of("Cypress", "End-to-end testing for modern web apps."),
      Pair.of("Playwright", "Cross-browser UI testing automation."),
      Pair.of("Selenium", "Web automation and browser testing."),
      Pair.of("Figma", "Designing UI mockups and prototypes."),
      Pair.of("Adobe XD", "Rapid prototyping and visual design."),
      Pair.of("UX Design", "Focusing on user experience and accessibility."),
      Pair.of("UI Design", "Creating visually appealing and usable interfaces."),
      Pair.of("Responsive Design", "Adapting layouts for multiple screen sizes."),
      Pair.of("Agile Methodology", "Working in sprints and iterative development."),
      Pair.of("Scrum", "Managing tasks and stand-ups in agile teams."),
      Pair.of("Kanban", "Visual workflow management for productivity."),
      Pair.of("Jira", "Tracking issues and project progress."),
      Pair.of("Confluence", "Documenting team knowledge and architecture."),
      Pair.of("Linux", "Navigating and managing systems via CLI."),
      Pair.of("Bash Scripting", "Automating tasks using shell scripts."),
      Pair.of("Nginx", "Configuring web servers and reverse proxies."),
      Pair.of("Apache", "Hosting and securing web applications."),
      Pair.of("Terraform", "Infrastructure as code for provisioning."),
      Pair.of("Ansible", "Automating server configuration."),
      Pair.of("CI/CD", "Automating build, test, and deploy workflows."),
      Pair.of("Unit Testing", "Ensuring component correctness through tests."),
      Pair.of("Integration Testing", "Verifying modules work together correctly."),
      Pair.of("End-to-End Testing", "Validating user journeys and app stability."),
      Pair.of("GitLab CI", "Implementing automated pipelines in GitLab."),
      Pair.of("API Testing", "Validating REST and GraphQL endpoints."),
      Pair.of("Swagger", "Documenting APIs clearly and interactively."),
      Pair.of("OpenAPI", "Designing standardized API contracts."),
      Pair.of("GraphQL", "Building flexible and efficient APIs."),
      Pair.of("Prisma", "Simplified database access with type safety."),
      Pair.of("Sequelize", "ORM for Node.js and relational databases."),
      Pair.of("Data Structures", "Understanding of lists, trees, and graphs."),
      Pair.of("Algorithms", "Problem-solving and optimization techniques."),
      Pair.of("Design Patterns", "Applying reusable architecture principles."),
      Pair.of("Microservices", "Designing modular and scalable applications."),
      Pair.of("Monorepos", "Managing multiple projects in a single repository."),
      Pair.of("RESTful Services", "Building predictable and standardized APIs."),
      Pair.of("Clean Architecture", "Separation of concerns and maintainable code."),
      Pair.of("Domain-Driven Design", "Structuring logic around real-world domains."),
      Pair.of("TDD", "Developing software guided by tests."),
      Pair.of("BDD", "Behavior-driven testing and requirements clarity."),
      Pair.of("SQL", "Writing complex joins, views, and transactions."),
      Pair.of("NoSQL", "Understanding schema-less databases."),
      Pair.of("Redis", "In-memory caching for performance optimization."),
      Pair.of("RabbitMQ", "Message queuing for async processing."),
      Pair.of("Kafka", "Streaming data processing and event pipelines."),
      Pair.of("Elasticsearch", "Searching and indexing large datasets."),
      Pair.of("Logstash", "Managing logs and data pipelines."),
      Pair.of("Kibana", "Visualizing and monitoring log data."),
      Pair.of("Prometheus", "Application metrics and monitoring."),
      Pair.of("Grafana", "Visual dashboards for system metrics."),
      Pair.of("Security Best Practices", "Following secure coding guidelines."),
      Pair.of("HTTPS", "Implementing SSL/TLS in web applications."),
      Pair.of("CSRF Protection", "Preventing cross-site request forgery attacks."),
      Pair.of("CORS", "Configuring cross-origin access safely."),
      Pair.of("Error Handling", "Graceful failure management in code."),
      Pair.of("Logging", "Structured application event tracking."),
      Pair.of("Performance Optimization", "Improving app response time and scalability."),
      Pair.of("Caching", "Enhancing performance with smart caching strategies."),
      Pair.of("Responsive UI", "Adaptive layouts for desktop and mobile."),
      Pair.of("Accessibility", "Designing for inclusive user experiences."),
      Pair.of("SEO Optimization", "Improving visibility of web content."),
      Pair.of("Version Control", "Branching and merging strategies in Git."),
      Pair.of("Team Collaboration", "Working effectively in agile teams."),
      Pair.of("Communication", "Clear technical and non-technical collaboration."),
      Pair.of("Problem Solving", "Identifying and resolving complex software issues.")
  );
  
  private static Pair<String, String> randomSkill() {
    return SKILLS.get(random.nextInt(SKILLS.size()));
  }
  
  @Override
  public void run(String... args) {
    LOGGER.info("Running data generator...");
    
    long userCount = userRepository.count();
    long skillCount = skillRepository.count();
    
    if (userCount > 0 || skillCount > 0) {
      LOGGER.info("Data already present ({} users, {} skills) — skipping generation.", userCount, skillCount);
      return;
    }
    
    generateUsersAndSkills();
  }
  
  private void generateUsersAndSkills() {
    List<User> users = new ArrayList<>();
    
    for (int i = 0; i <= 1; i++) {
      User admin = new User();
      admin.setUsername(USERNAMES.get(i));
      admin.setPassword(passwordEncoder.encode("admin123"));
      admin.setRole(Role.ADMIN);
      users.add(admin);
    }
    
    for (int i = 2; i <= 9; i++) {
      User user = new User();
      user.setUsername(USERNAMES.get(i));
      user.setPassword(passwordEncoder.encode("user123"));
      user.setRole(Role.USER);
      users.add(user);
    }
    
    userRepository.saveAll(users);
    LOGGER.info("Created {} users (2 admins, 8 regular).", users.size());
    
    List<Skill> allSkills = new ArrayList<>();
    List<SkillProgress> allProgress = new ArrayList<>();
    for (User user : users) {
      for (int i = 0; i < 10; i++) {
        Pair<String, String> skillInfo = randomSkill();
        Skill skill = new Skill();
        skill.setName(skillInfo.getLeft());
        skill.setDescription(skillInfo.getRight());
        skill.setUser(user);
        skill.setTargetProgressMinutes(random.nextInt(1800, 3600));
        for(int j = 0; j < 10; j++) {
          int targetProgress = random.nextInt(60, 180);
          skill.addTime(targetProgress);
          SkillProgress skillProgress = new SkillProgress(
              skill,
              user,
              "pre generated note: " + j,
              targetProgress
          );
          allProgress.add(skillProgress);
        }
        skill.setProgress(skill.computeProgressPercentage());
        allSkills.add(skill);
      }
    }
    
    skillRepository.saveAll(allSkills);
    skillProgressRepository.saveAll(allProgress);
    LOGGER.info("Created {} skills (10 per user) and {} progress updates.", allSkills.size(), allProgress.size());
  }
}
