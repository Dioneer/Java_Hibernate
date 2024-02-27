package Pegas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_chat", schema = "public")
public class UserChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    private Instant created_at;
    private String created_by;

    public void setChat(Chat chat){
        this.chat = chat;
        chat.getUserChats().add(this);
    }
    public void setUser(User user){
        this.user = user;
        user.getUserChats().add(this);
    }
}
