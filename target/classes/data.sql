-- Users Seed Data
INSERT INTO users (username, password, email, role, unique_id, status, full_name, department, is_active) VALUES
('admin', 'admin123', 'admin@interview.com', 'ADMIN', 'admin001', 'ONLINE', 'Admin User', 'Management', TRUE),
('sender1', 'sender123', 'sender1@interview.com', 'SENDER', 'snd001', 'ONLINE', 'John Sender', 'HR', TRUE),
('receiver1', 'receiver123', 'receiver1@interview.com', 'RECEIVER', 'rcv1001', 'ONLINE', 'Alice Receiver', 'IT', TRUE),
('receiver2', 'receiver123', 'receiver2@interview.com', 'RECEIVER', 'rcv1002', 'OFFLINE', 'Bob Receiver', 'IT', TRUE),
('sender2', 'sender123', 'sender2@interview.com', 'SENDER', 'snd002', 'OFFLINE', 'Jane Smith', 'HR', TRUE);

-- Comprehensive Interview Questions Seed Data
INSERT INTO questions (keyword, question, answer, category, difficulty, tags, is_active, usage_count) VALUES

-- ================= CORE JAVA =================
('java,jvm,jre,jdk,features',
'What are the main features of Java?',
'Java is platform independent (Write Once Run Anywhere), object-oriented, strongly typed, secure, supports automatic memory management with Garbage Collection, multithreaded, and has a robust ecosystem.',
'Core Java', 'EASY', 'java,fundamentals', TRUE, 0),

('jvm,java virtual machine',
'What is JVM and how does it work?',
'JVM (Java Virtual Machine) is responsible for executing bytecode. It consists of Class Loader, Memory Area, Execution Engine, and Native Interface. It provides platform independence by converting bytecode to machine-specific instructions.',
'Core Java', 'EASY', 'java,jvm', TRUE, 0),

('jdk,jre,jvm,difference',
'Difference between JDK, JRE, and JVM?',
'JDK (Java Development Kit) includes development tools + JRE + JVM, JRE (Java Runtime Environment) includes JVM + libraries, JVM (Java Virtual Machine) executes bytecode.',
'Core Java', 'EASY', 'java,jdk,jre', TRUE, 0),

('java 8,lambda,streams,features',
'What are new features in Java 8?',
'Java 8 introduced Lambda expressions, Stream API, Functional interfaces, Optional class, Default methods in interfaces, Date and Time API, Nashorn JavaScript engine, and Parallel Array sorting.',
'Core Java', 'MEDIUM', 'java,java8', TRUE, 0),

('java 17,records,sealed classes,features',
'What are new features in Java 17?',
'Java 17 introduced Records for immutable data carriers, Sealed Classes for restricted inheritance, Pattern Matching for instanceof, Text Blocks, Enhanced Switch, and Foreign Function & Memory API.',
'Core Java', 'MEDIUM', 'java,java17', TRUE, 0),

('string,immutable,thread safe',
'Why is String immutable in Java?',
'String is immutable for security, thread safety, caching (String pool), and hashcode caching. Immutability ensures that once created, String objects cannot be modified.',
'Core Java', 'MEDIUM', 'java,string', TRUE, 0),

('hashcode,equals,contract',
'Explain hashCode() and equals() contract?',
'If two objects are equal according to equals(), they must return same hashCode(). Unequal objects can have same hashCode (hash collision). Consistent hashCode during execution is required.',
'Core Java', 'MEDIUM', 'java,hashcode', TRUE, 0),

('final,finally,finalize,difference',
'Difference between final, finally, and finalize?',
'final is a keyword for variables/methods/classes that cannot be modified/overridden/inherited. finally is a block in try-catch that always executes. finalize() is a method called before garbage collection.',
'Core Java', 'MEDIUM', 'java,keywords', TRUE, 0),

('serialization,externalizable',
'What is serialization in Java?',
'Serialization converts objects to byte stream for storage/transmission. Externalizable provides custom serialization control. serialVersionUID ensures version compatibility.',
'Core Java', 'MEDIUM', 'java,serialization', TRUE, 0),

('classloader,binary name',
'Explain Java ClassLoader hierarchy?',
'The hierarchy has three loaders: Bootstrap (loads core Java classes), Extension (loads extension classes), and Application (loads application classes). It follows delegation model for security and avoids loading same class multiple times.',
'Core Java', 'HARD', 'java,classloader', TRUE, 0),

-- ================= OOPS =================
('encapsulation,oops,abstraction',
'What is encapsulation with example?',
'Encapsulation is wrapping data and methods together and restricting direct access using access modifiers (private, protected, public). Example: private fields with public getters/setters.',
'OOP', 'MEDIUM', 'oop,encapsulation', TRUE, 0),

('inheritance,oops,multiple inheritance',
'What is inheritance and why Java does not support multiple inheritance?',
'Inheritance allows a class to acquire properties of another class using extends. Java avoids multiple inheritance of classes to prevent diamond problem, but supports multiple inheritance of interfaces.',
'OOP', 'MEDIUM', 'oop,inheritance', TRUE, 0),

('polymorphism,oops,method overloading,overriding',
'What is polymorphism with examples?',
'Polymorphism allows methods to behave differently. Compile-time polymorphism: method overloading (same name, different parameters). Runtime polymorphism: method overriding (subclass provides specific implementation).',
'OOP', 'MEDIUM', 'oop,polymorphism', TRUE, 0),

('abstraction,oops,interface,abstract class',
'Difference between abstract class and interface?',
'Abstract class can have constructors, instance variables, and implemented methods. Interface can only have abstract methods (before Java 8), static and default methods (Java 8+). A class can extend one abstract class but implement multiple interfaces.',
'OOP', 'MEDIUM', 'oop,abstraction', TRUE, 0),

('composition,aggregation,association',
'Difference between composition, aggregation, and association?',
'Association: relationship between objects. Aggregation: has-a relationship where child can exist independently (weak relationship). Composition: strong has-a where child cannot exist without parent (strong relationship).',
'OOP', 'HARD', 'oop,design', TRUE, 0),

('solid principles,design patterns',
'Explain SOLID principles?',
'S - Single Responsibility (one reason to change), O - Open/Closed (open for extension, closed for modification), L - Liskov Substitution (subtypes must be substitutable), I - Interface Segregation (many specific interfaces), D - Dependency Inversion (depend on abstractions).',
'OOP', 'HARD', 'oop,solid', TRUE, 0),

-- ================= COLLECTIONS =================
('list,set,arraylist,linkedlist',
'Difference between List and Set?',
'List allows duplicates, maintains insertion order, allows null values. Set does not allow duplicates, does not maintain order (except LinkedHashSet), allows one null.',
'Collections', 'MEDIUM', 'collections,list,set', TRUE, 0),

('map,hashmap,key value',
'When would you use Map over List?',
'Use Map for key-value lookups (O(1) access), unique keys. Use List for ordered collections, duplicate values, index-based access.',
'Collections', 'MEDIUM', 'collections,map', TRUE, 0),

('arraylist,linkedlist,difference',
'Difference between ArrayList and LinkedList?',
'ArrayList: dynamic array, fast random access (O(1)), slow insert/delete in middle (O(n)). LinkedList: doubly-linked list, slow random access (O(n)), fast insert/delete (O(1)).',
'Collections', 'MEDIUM', 'collections,performance', TRUE, 0),

('hashmap,concurrenthashmap',
'Difference between HashMap and ConcurrentHashMap?',
'HashMap is not thread-safe, allows null key/values. ConcurrentHashMap is thread-safe, does not allow null, uses segment locking for better concurrency than Hashtable.',
'Collections', 'HARD', 'collections,concurrency', TRUE, 0),

('treemap,hashmap,sorting',
'Difference between TreeMap and HashMap?',
'TreeMap implements SortedMap, maintains keys in sorted order (Red-Black tree), O(log n) operations. HashMap uses hash table, O(1) operations, no guaranteed order.',
'Collections', 'HARD', 'collections,sorting', TRUE, 0),

('iterator,listiterator,enumeration',
'Difference between Iterator and ListIterator?',
'Iterator allows forward traversal only. ListIterator allows bidirectional traversal, can modify elements during iteration, and provides index information.',
'Collections', 'MEDIUM', 'collections,iterator', TRUE, 0),

-- ================= EXCEPTIONS =================
('exception,checked,unchecked',
'Difference between checked and unchecked exceptions?',
'Checked exceptions must be declared or caught (compile-time checked): IOException, SQLException. Unchecked exceptions are RuntimeExceptions: NullPointerException, ArithmeticException.',
'Exception Handling', 'MEDIUM', 'exceptions,handling', TRUE, 0),

('try with resources,auto close',
'What is try-with-resources?',
'It automatically closes resources that implement AutoCloseable interface. Resources are closed in reverse order of their creation, even if exception occurs.',
'Exception Handling', 'MEDIUM', 'exceptions,resources', TRUE, 0),

('throw,throws,difference',
'Difference between throw and throws?',
'throw is used to explicitly throw an exception. throws is used in method signature to declare exceptions that method can throw.',
'Exception Handling', 'MEDIUM', 'exceptions,keywords', TRUE, 0),

('custom exception,how to create',
'How to create custom exception?',
'Extend Exception (for checked) or RuntimeException (for unchecked). Add constructors, override toString() if needed. Use meaningful exception names.',
'Exception Handling', 'EASY', 'exceptions,custom', TRUE, 0),

('error,exception,difference',
'Difference between Error and Exception?',
'Error represents serious problems that applications should not try to catch (OutOfMemoryError, StackOverflowError). Exception represents conditions that applications might want to catch.',
'Exception Handling', 'MEDIUM', 'exceptions,error', TRUE, 0),

-- ================= MULTITHREADING =================
('thread,multithreading,process',
'What is a thread and difference with process?',
'A thread is a lightweight unit of execution within a process. Process is a program in execution with its own memory space. Threads share process memory.',
'Multithreading', 'MEDIUM', 'threads,concurrency', TRUE, 0),

('runnable,callable,difference',
'Difference between Runnable and Callable?',
'Runnable does not return result, cannot throw checked exceptions. Callable returns a value (Future), can throw checked exceptions.',
'Multithreading', 'MEDIUM', 'threads,concurrent', TRUE, 0),

('synchronization,race condition',
'What is synchronization and types?',
'Synchronization controls access to shared resources. Types: synchronized method (locks entire object), synchronized block (locks specific object), static synchronized (locks class object).',
'Multithreading', 'HARD', 'threads,synchronization', TRUE, 0),

('deadlock,prevention',
'What is deadlock and how to prevent it?',
'Deadlock occurs when two or more threads wait for each other to release locks. Prevention: acquire locks in consistent order, use timeout, avoid nested locks, use Lock interface with tryLock().',
'Multithreading', 'HARD', 'threads,deadlock', TRUE, 0),

('volatile,thread safety',
'What is volatile keyword?',
'volatile ensures visibility of changes to variables across threads, prevents instruction reordering. Used for flags, boolean status variables. Not suitable for compound operations.',
'Multithreading', 'HARD', 'threads,volatile', TRUE, 0),

('executor service,thread pool',
'What is ExecutorService?',
'ExecutorService manages thread pool, submits tasks for execution, returns Future objects. Methods: execute(), submit(), invokeAll(), shutdown(). Replaces manual thread management.',
'Multithreading', 'MEDIUM', 'threads,executor', TRUE, 0),

('completablefuture,asynchronous',
'What is CompletableFuture?',
'CompletableFuture enables asynchronous programming with callbacks. It supports chaining, combining multiple futures, exception handling, and non-blocking operations.',
'Multithreading', 'HARD', 'threads,async', TRUE, 0),

-- ================= STREAMS & LAMBDA =================
('stream,lambda,java8',
'What are Java Streams?',
'Streams provide functional-style operations on collections. They support map, filter, reduce, collect operations. Streams are lazy, can be parallelized, and process data declaratively.',
'Java 8', 'MEDIUM', 'java,streams', TRUE, 0),

('lambda,expression,syntax',
'Explain lambda expression syntax?',
'Lambda: (parameters) -> expression or (parameters) -> { statements }. Used with functional interfaces (Single Abstract Method). Example: (a, b) -> a + b.',
'Java 8', 'EASY', 'java,lambda', TRUE, 0),

('optional,null safety',
'What is Optional class?',
'Optional is a container object that may or may not contain non-null value. Helps avoid NullPointerException. Methods: of(), ofNullable(), isPresent(), orElse(), orElseThrow().',
'Java 8', 'MEDIUM', 'java,optional', TRUE, 0),

('parallel stream,performance',
'When to use parallel streams?',
'Use parallel streams for large datasets, CPU-intensive operations. Not suitable for I/O-bound tasks or small datasets due to overhead. Use ForkJoinPool.commonPool().',
'Java 8', 'MEDIUM', 'java,parallel', TRUE, 0),

-- ================= SPRING FRAMEWORK =================
('spring,dependency injection,di',
'What is dependency injection in Spring?',
'DI is a design pattern where Spring container injects dependencies (objects) into a class. Types: Constructor injection (recommended), Setter injection, Field injection. Achieves loose coupling.',
'Spring', 'MEDIUM', 'spring,di', TRUE, 0),

('ioc container,bean',
'What is IoC Container?',
'IoC (Inversion of Control) Container manages bean lifecycle, dependency injection, and configuration. It creates, wires, and manages objects. ApplicationContext is the main interface.',
'Spring', 'MEDIUM', 'spring,ioc', TRUE, 0),

('spring bean,scope',
'What are Spring Bean scopes?',
'Singleton (default, one instance per container), Prototype (new instance each request), Request (one per HTTP request), Session (one per HTTP session), Application (one per ServletContext).',
'Spring', 'MEDIUM', 'spring,bean', TRUE, 0),

('autowired,annotation',
'What is @Autowired?',
'@Autowired injects dependencies automatically. Can be used on fields, constructors, setters. Spring resolves bean by type. Use @Qualifier for multiple beans of same type.',
'Spring', 'EASY', 'spring,annotations', TRUE, 0),

('component,service,repository',
'Explain Spring stereotypes?',
'@Component: generic stereotype. @Service: for service layer. @Repository: for DAO layer (exception translation). @Controller: for presentation layer (MVC).',
'Spring', 'EASY', 'spring,annotations', TRUE, 0),

('aop,aspect oriented programming',
'What is AOP in Spring?',
'AOP (Aspect-Oriented Programming) separates cross-cutting concerns (logging, security, transactions) from business logic. Uses aspects, pointcuts, and advice.',
'Spring', 'HARD', 'spring,aop', TRUE, 0),

('transaction,management',
'How does Spring handle transactions?',
'@Transactional manages database transactions declaratively. Supports propagation, isolation levels, rollback rules. Works with JPA, JDBC, JTA.',
'Spring', 'MEDIUM', 'spring,transaction', TRUE, 0),

-- ================= SPRING BOOT =================
('spring boot,auto configuration',
'What is Spring Boot?',
'Spring Boot simplifies Spring development with auto-configuration, embedded server (Tomcat), starter dependencies, and production-ready features (metrics, health checks).',
'Spring Boot', 'EASY', 'spring,boot', TRUE, 0),

('spring boot starter,dependencies',
'What are Spring Boot Starters?',
'Starters are convenient dependency descriptors. Examples: spring-boot-starter-web (for web apps), spring-boot-starter-data-jpa (for database), spring-boot-starter-security (for security).',
'Spring Boot', 'EASY', 'spring,starter', TRUE, 0),

('restcontroller,api spring',
'What is @RestController?',
'@RestController combines @Controller and @ResponseBody. It automatically serializes return objects to JSON/XML. Used for building RESTful APIs.',
'Spring Boot', 'EASY', 'spring,rest', TRUE, 0),

('spring boot actuator',
'What is Spring Boot Actuator?',
'Actuator provides production-ready features: health checks, metrics, info, env, loggers, thread dump. Endpoints: /actuator/health, /actuator/metrics.',
'Spring Boot', 'MEDIUM', 'spring,actuator', TRUE, 0),

('profile,spring configuration',
'What are Spring Profiles?',
'Profiles allow environment-specific configuration. Use @Profile annotation or spring.profiles.active property. Example: dev, test, prod profiles.',
'Spring Boot', 'MEDIUM', 'spring,configuration', TRUE, 0),

-- ================= SPRING DATA JPA =================
('entity,jpa,annotation',
'What is @Entity in JPA?',
'@Entity marks a class as JPA entity, mapped to database table. Requires @Id for primary key. Supports @Column, @OneToMany, @ManyToOne, @ManyToMany for relationships.',
'Spring Data JPA', 'MEDIUM', 'jpa,entity', TRUE, 0),

('repository,crud,interface',
'What is Spring Data JPA Repository?',
'Repository interface provides CRUD operations automatically. Extend JpaRepository for full CRUD, paging, sorting. Custom queries using @Query or method naming.',
'Spring Data JPA', 'MEDIUM', 'jpa,repository', TRUE, 0),

('lazy,eager,fetch',
'Difference between LAZY and EAGER fetch?',
'EAGER loads related entities immediately. LAZY loads on demand (proxies). LAZY is default for better performance, but can cause LazyInitializationException.',
'Spring Data JPA', 'MEDIUM', 'jpa,fetch', TRUE, 0),

('transactional,isolation',
'What are transaction isolation levels?',
'READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE. Each provides different levels of consistency and locking. Default depends on database.',
'Spring Data JPA', 'HARD', 'jpa,transaction', TRUE, 0),

-- ================= MICROSERVICES =================
('microservices,monolith,difference',
'What are microservices?',
'Microservices architecture breaks application into small, independent services communicating via APIs. Benefits: scalability, fault isolation, technology diversity. Challenges: distributed complexity, data consistency.',
'Microservices', 'MEDIUM', 'microservices,basics', TRUE, 0),

('api gateway,pattern',
'What is API Gateway?',
'API Gateway is a single entry point for all client requests. Handles routing, load balancing, authentication, rate limiting, caching. Examples: Netflix Zuul, Spring Cloud Gateway.',
'Microservices', 'MEDIUM', 'microservices,patterns', TRUE, 0),

('service discovery,eureka',
'What is service discovery?',
'Service discovery allows services to find each other dynamically. Netflix Eureka server maintains registry. Services register with Eureka and discover other services.',
'Microservices', 'MEDIUM', 'microservices,discovery', TRUE, 0),

('circuit breaker,resilience',
'What is Circuit Breaker pattern?',
'Circuit Breaker prevents cascading failures. When a service fails repeatedly, it trips the circuit and redirects to fallback. Implementation: Resilience4j, Hystrix.',
'Microservices', 'HARD', 'microservices,patterns', TRUE, 0),

('distributed tracing,zipkin',
'What is distributed tracing?',
'Distributed tracing tracks requests across multiple microservices. Tools: Zipkin, Jaeger. Helps identify performance bottlenecks and failures.',
'Microservices', 'HARD', 'microservices,observability', TRUE, 0),

('event driven,kafka',
'What is event-driven architecture?',
'EDA uses events for communication between services. Producers publish events to message brokers (Kafka, RabbitMQ), consumers subscribe. Benefits: loose coupling, scalability.',
'Microservices', 'MEDIUM', 'microservices,architecture', TRUE, 0),

-- ================= DATABASE & SQL =================
('sql,primary key,unique',
'What is primary key?',
'Primary key uniquely identifies each row in a table. Cannot be null, must be unique. Each table can have only one primary key.',
'Database', 'EASY', 'database,sql', TRUE, 0),

('sql,join,inner join,left join',
'What are different types of JOINs?',
'INNER JOIN: matching rows in both tables. LEFT JOIN: all from left, matching from right. RIGHT JOIN: all from right, matching from left. FULL JOIN: all rows from both tables.',
'Database', 'MEDIUM', 'database,sql,joins', TRUE, 0),

('where,having,sql difference',
'Difference between WHERE and HAVING?',
'WHERE filters rows before GROUP BY aggregation. HAVING filters after aggregation. WHERE cannot use aggregate functions, HAVING can.',
'Database', 'MEDIUM', 'database,sql', TRUE, 0),

('index,performance',
'What is database index?',
'Index speeds up data retrieval (like book index). Trade-offs: faster reads, slower writes, uses storage. Types: B-tree, Hash, Full-text. Create on frequently queried columns.',
'Database', 'MEDIUM', 'database,performance', TRUE, 0),

('acid,properties',
'What are ACID properties?',
'Atomicity (all or nothing), Consistency (valid state), Isolation (concurrent transactions don''t interfere), Durability (committed changes persist).',
'Database', 'MEDIUM', 'database,transaction', TRUE, 0),

('normalization,denormalization',
'Difference between normalization and denormalization?',
'Normalization reduces redundancy, improves data integrity (1NF, 2NF, 3NF, BCNF). Denormalization improves read performance by adding redundancy for complex queries.',
'Database', 'HARD', 'database,design', TRUE, 0),

('transaction,isolation levels',
'What are transaction isolation levels?',
'READ UNCOMMITTED (dirty reads), READ COMMITTED (no dirty reads), REPEATABLE_READ (no phantom reads), SERIALIZABLE (complete isolation). Higher levels = more locking.',
'Database', 'HARD', 'database,transaction', TRUE, 0),

-- ================= REACT =================
('react,component,jsx',
'What are React components?',
'Components are reusable building blocks. Types: Functional components (preferred) and Class components. JSX allows writing HTML-like syntax in JavaScript.',
'React', 'EASY', 'react,basics', TRUE, 0),

('usestate,useeffect,hooks',
'What are React Hooks?',
'Hooks allow using state and lifecycle features in functional components. useState for state management, useEffect for side effects, useContext for context, useRef for DOM access.',
'React', 'MEDIUM', 'react,hooks', TRUE, 0),

('virtual dom,performance',
'What is Virtual DOM?',
'Virtual DOM is a lightweight JavaScript representation of real DOM. React compares virtual DOM states, calculates minimal changes (diffing), and updates real DOM efficiently.',
'React', 'MEDIUM', 'react,performance', TRUE, 0),

('props,drilling',
'What is prop drilling?',
'Prop drilling is passing data through multiple component layers to reach deeply nested components. Alternatives: Context API, Redux, state management libraries.',
'React', 'MEDIUM', 'react,architecture', TRUE, 0),

('usecallback,usememo,optimization',
'Difference between useCallback and useMemo?',
'useCallback memoizes function reference, prevents unnecessary re-renders when passed as prop. useMemo memoizes computed value, recalculates only when dependencies change.',
'React', 'HARD', 'react,optimization', TRUE, 0),

('redux,state management',
'What is Redux?',
'Redux is a state management library. Core concepts: Store (state container), Action (plain object describing change), Reducer (pure function to update state), Dispatch (sends actions).',
'React', 'MEDIUM', 'react,redux', TRUE, 0),

-- ================= SYSTEM DESIGN =================
('scalability,vertical,horizontal',
'Difference between vertical and horizontal scaling?',
'Vertical scaling: add more resources (CPU, RAM) to single server. Horizontal scaling: add more servers. Horizontal is better for fault tolerance and unlimited scaling.',
'System Design', 'MEDIUM', 'system,scalability', TRUE, 0),

('load balancer,algorithms',
'What is load balancing?',
'Load balancer distributes incoming traffic across multiple servers. Algorithms: Round Robin, Least Connections, IP Hash. Benefits: high availability, scalability, fault tolerance.',
'System Design', 'MEDIUM', 'system,loadbalancer', TRUE, 0),

('caching,strategies',
'What are caching strategies?',
'Cache-aside: application manages cache. Write-through: write to cache and DB simultaneously. Write-back: write to cache first, DB later. Write-around: write to DB only, cache on miss.',
'System Design', 'MEDIUM', 'system,caching', TRUE, 0),

('cdn,content delivery',
'What is CDN?',
'CDN (Content Delivery Network) distributes content across geographically distributed servers. Reduces latency, improves load times, handles traffic spikes. Examples: Cloudflare, AWS CloudFront.',
'System Design', 'EASY', 'system,cdn', TRUE, 0),

('cap theorem,distributed',
'What is CAP theorem?',
'CAP theorem: In distributed systems, can only guarantee 2 of 3: Consistency (all nodes see same data), Availability (every request gets response), Partition tolerance (system continues despite network failures).',
'System Design', 'HARD', 'system,cap', TRUE, 0),

-- ================= DATA STRUCTURES & ALGORITHMS =================
('array,linkedlist,pros cons',
'Difference between array and linked list?',
'Array: contiguous memory, O(1) random access, fixed size (in most languages). Linked list: non-contiguous, O(n) access, dynamic size, efficient insert/delete.',
'DSA', 'EASY', 'dsa,basics', TRUE, 0),

('stack,queue,applications',
'Difference between stack and queue?',
'Stack: LIFO (Last In First Out) - undo operations, function calls. Queue: FIFO (First In First Out) - task scheduling, printer spooler.',
'DSA', 'EASY', 'dsa,basics', TRUE, 0),

('tree,binary search tree',
'What is Binary Search Tree?',
'BST is a binary tree where left child < parent < right child. Enables O(log n) search, insert, delete operations. Degenerates to O(n) if unbalanced.',
'DSA', 'MEDIUM', 'dsa,trees', TRUE, 0),

('hash table,collision resolution',
'How do hash tables handle collisions?',
'Collision resolution: Chaining (linked list at each bucket), Open Addressing (linear/quadratic probing), Rehashing. Load factor affects performance.',
'DSA', 'MEDIUM', 'dsa,hashtable', TRUE, 0),

('big o,complexity',
'What is Big O notation?',
'Big O describes worst-case time/space complexity. Common: O(1) constant, O(log n) logarithmic, O(n) linear, O(n log n) linearithmic, O(n²) quadratic.',
'DSA', 'EASY', 'dsa,complexity', TRUE, 0),

('sorting algorithms,comparison',
'Compare sorting algorithms?',
'Quick Sort: O(n log n) average, in-place, not stable. Merge Sort: O(n log n), stable, needs O(n) space. Bubble Sort: O(n²), simple, stable. Heap Sort: O(n log n), in-place, not stable.',
'DSA', 'MEDIUM', 'dsa,sorting', TRUE, 0),

-- ================= DEVOPS & CLOUD =================
('docker,container',
'What is Docker?',
'Docker is containerization platform. Containers are lightweight, isolated environments with application and dependencies. Benefits: consistency, portability, resource efficiency.',
'DevOps', 'EASY', 'devops,docker', TRUE, 0),

('kubernetes,orchestration',
'What is Kubernetes?',
'K8s is container orchestration platform. Features: auto-scaling, load balancing, self-healing, rolling updates, storage orchestration. Manages containers across clusters.',
'DevOps', 'MEDIUM', 'devops,kubernetes', TRUE, 0),

('ci cd pipeline',
'What is CI/CD?',
'CI (Continuous Integration): automated testing and integration of code changes. CD (Continuous Deployment): automated release to production. Tools: Jenkins, GitLab CI, GitHub Actions.',
'DevOps', 'MEDIUM', 'devops,cicd', TRUE, 0),

('aws,cloud services',
'What are key AWS services?',
'EC2 (compute), S3 (storage), RDS (database), Lambda (serverless), CloudFront (CDN), VPC (networking), IAM (security), CloudWatch (monitoring).',
'Cloud', 'MEDIUM', 'cloud,aws', TRUE, 0),

('infrastructure as code,terraform',
'What is Infrastructure as Code?',
'IaC manages infrastructure through code (not manual). Tools: Terraform, CloudFormation, Ansible. Benefits: version control, reproducibility, consistency.',
'DevOps', 'MEDIUM', 'devops,iac', TRUE, 0),

-- ================= SECURITY =================
('jwt,authentication,authorization',
'What is JWT?',
'JWT (JSON Web Token) is stateless authentication. Contains header, payload, signature. Used for REST APIs. Benefits: scalable, no server-side session storage.',
'Security', 'MEDIUM', 'security,jwt,auth', TRUE, 0),

('oauth,openid connect',
'What is OAuth 2.0?',
'OAuth 2.0 is authorization framework (third-party access). OpenID Connect adds authentication on top of OAuth. Used for social login (Google, GitHub).',
'Security', 'MEDIUM', 'security,oauth', TRUE, 0),

('xss,csrf,prevention',
'What are XSS and CSRF?',
'XSS (Cross-Site Scripting): inject malicious scripts. Prevention: input validation, output encoding, CSP. CSRF (Cross-Site Request Forgery): unauthorized actions. Prevention: CSRF tokens, SameSite cookies.',
'Security', 'HARD', 'security,web', TRUE, 0),

('encryption,symmetric,asymmetric',
'Difference between symmetric and asymmetric encryption?',
'Symmetric: same key for encryption/decryption (AES, DES). Faster, key distribution challenge. Asymmetric: public/private key pair (RSA). Slower, solves key distribution.',
'Security', 'MEDIUM', 'security,encryption', TRUE, 0),

('sql injection,prevention',
'What is SQL injection and how to prevent?',
'SQL injection: malicious SQL via user input. Prevention: parameterized queries (PreparedStatement), input validation, ORM frameworks, principle of least privilege.',
'Security', 'MEDIUM', 'security,database', TRUE, 0),

-- ================= BEHAVIORAL & HR =================
('problem solving,debugging,production issue',
'How do you debug a production issue?',
'Reproduce issue, check logs, isolate root cause, fix issue, add monitoring/alerting, test thoroughly, deploy with rollback plan.',
'Behavioral', 'HARD', 'behavioral,debugging', TRUE, 0),

('strengths,weaknesses',
'How do you answer "What are your strengths and weaknesses?"',
'Strengths: relevant skills (problem-solving, communication, teamwork). Weaknesses: genuine but manageable (public speaking, delegation), show improvement plan.',
'Behavioral', 'EASY', 'behavioral,hr', TRUE, 0),

('tell me about yourself',
'How to structure "Tell me about yourself"?',
'Present: current role. Past: relevant experience (2-3 key points). Future: why this role/company. Keep it 2-3 minutes, focused on professional achievements.',
'Behavioral', 'EASY', 'behavioral,hr', TRUE, 0),

('why this company',
'How to answer "Why do you want to work here?"',
'Research company: products, culture, values. Connect your skills to their needs. Show genuine interest. Mention specific projects or initiatives.',
'Behavioral', 'EASY', 'behavioral,hr', TRUE, 0),

('challenges,overcome',
'How to describe a challenge you overcame?',
'Star method: Situation, Task, Action, Result. Choose professional challenge. Focus on your actions and learnings, not blaming others.',
'Behavioral', 'MEDIUM', 'behavioral,hr', TRUE, 0),

('leadership,team management',
'How do you handle conflict in team?',
'Listen actively, understand perspectives, find common ground, focus on shared goals, address issues privately, follow up to ensure resolution.',
'Behavioral', 'MEDIUM', 'behavioral,leadership', TRUE, 0),

('tight deadline,pressure',
'How do you handle tight deadlines?',
'Prioritize tasks, communicate early about risks, break down work, focus on MVP, ask for help when needed, maintain quality under pressure.',
'Behavioral', 'MEDIUM', 'behavioral,workstyle', TRUE, 0);
