<script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { useCommentStore } from '@/store/comment'
  import { useMemberStore } from '@/store/user'

  const route = useRoute()
  const articleNo = route.params.id
  const commentStore = useCommentStore()
  const memberStore = useMemberStore()

  const newComment = ref('')
  const replyText = ref('')
  const replyTargetId = ref(null)

  onMounted(() => {
    commentStore.fetchComments(articleNo)
  })

  const addComment = () => {
    if (!newComment.value.trim()) return
    commentStore.addComment({
      content: newComment.value,
      usersNo: memberStore.loginUser.user.no,
      articleNo,
    })
    newComment.value = ''
  }

  const addReply = (parentNo) => {
    if (!replyText.value.trim()) return
    commentStore.addComment({
      content: replyText.value,
      usersNo: memberStore.loginUser.user.no,
      articleNo,
      parentNo,
    })
    replyText.value = ''
    replyTargetId.value = null
  }

  const onDelete = async (comment) => {
    const ok = confirm('정말 삭제하시겠습니까?')
    if (ok) {
      await commentStore.deleteComment(comment.no, articleNo)
    }
  }
</script>

<template>
  <div class="comment-section">
    <v-card class="comment-card" elevation="8" rounded="xl">
      <v-card-title class="comment-header">
        💬 댓글 💭
      </v-card-title>

      <v-card-text class="comment-content">
        <!-- 새 댓글 작성 -->
        <div class="new-comment-section">
          <v-textarea
            v-model="newComment"
            label="✨ 댓글을 입력하세요"
            auto-grow
            rows="3"
            variant="outlined"
            color="cyan"
            class="comment-input"
            rounded="lg"
            hide-details
          />
          <v-btn
            class="submit-comment-btn"
            color="cyan"
            @click="addComment"
            rounded="xl"
            elevation="4"
          >
            🚀 댓글 작성
          </v-btn>
        </div>

        <!-- 댓글 목록 -->
        <div class="comments-list">
          <div v-for="comment in commentStore.comments" :key="comment.no" class="comment-item">
            <v-card class="individual-comment" elevation="4" rounded="lg">
              <v-card-text>
                <div class="comment-meta">
                  <span class="comment-author">👤 {{ comment.username }}</span>
                  <span class="comment-date">📅 {{ comment.regDate }}</span>
                </div>
                <p class="comment-text">{{ comment.content }}</p>

                <div class="comment-actions">
                  <v-btn
                    size="small"
                    variant="text"
                    color="cyan"
                    @click="replyTargetId = replyTargetId === comment.no ? null : comment.no"
                    class="action-btn"
                  >
                    💬 답글
                  </v-btn>
                  <v-btn
                    v-if="comment.usersNo === memberStore.loginUser.user.no"
                    size="small"
                    variant="text"
                    color="red"
                    @click="onDelete(comment)"
                    class="action-btn"
                  >
                    🗑️ 삭제
                  </v-btn>
                </div>

                <!-- 답글 작성 폼 -->
                <div v-if="replyTargetId === comment.no" class="reply-form">
                  <v-textarea
                    v-model="replyText"
                    label="💭 답글 입력"
                    auto-grow
                    rows="2"
                    variant="outlined"
                    color="teal"
                    rounded="lg"
                    class="reply-input"
                  />
                  <v-btn
                    size="small"
                    color="teal"
                    class="reply-submit-btn"
                    @click="addReply(comment.no)"
                    rounded="xl"
                    elevation="3"
                  >
                    ✅ 답글 작성
                  </v-btn>
                </div>
              </v-card-text>
            </v-card>

            <!-- 대댓글 목록 -->
            <div v-for="reply in comment.children" :key="reply.no" class="reply-item">
              <v-card class="reply-comment" elevation="2" rounded="lg">
                <v-card-text>
                  <div class="comment-meta">
                    <span class="comment-author">👤 {{ reply.username }}</span>
                    <span class="comment-date">📅 {{ reply.regDate }}</span>
                  </div>
                  <p class="comment-text">{{ reply.content }}</p>

                  <div class="comment-actions" v-if="reply.usersNo === memberStore.loginUser.user.no">
                    <v-btn
                      size="small"
                      variant="text"
                      color="red"
                      @click="onDelete(reply)"
                      class="action-btn"
                    >
                      🗑️ 삭제
                    </v-btn>
                  </div>
                </v-card-text>
              </v-card>
            </div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<style scoped>
  .comment-section {
    margin: 30px 0;
  }

  .comment-card {
    background: linear-gradient(135deg, rgba(255,255,255,0.95), rgba(240,248,255,0.95));
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255,255,255,0.3);
  }

  .comment-header {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-align: center;
    font-weight: 700;
    padding: 20px;
  }

  .comment-content {
    padding: 30px;
  }

  .new-comment-section {
    margin-bottom: 30px;
    padding: 25px;
    background: linear-gradient(135deg, rgba(0,188,212,0.05), rgba(0,150,136,0.05));
    border-radius: 20px;
    border: 1px solid rgba(0,188,212,0.2);
  }

  .comment-input {
    margin-bottom: 20px;
  }

  .comment-input :deep(.v-field) {
    background: linear-gradient(135deg, rgba(255,255,255,0.9), rgba(240,248,255,0.9));
    border-radius: 15px;
    box-shadow: 0 4px 15px rgba(0,188,212,0.1);
  }

  .submit-comment-btn {
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .submit-comment-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(0,188,212,0.3);
  }

  .comments-list {
    space-y: 20px;
  }

  .comment-item {
    margin-bottom: 25px;
  }

  .individual-comment {
    background: linear-gradient(135deg, rgba(255,255,255,0.9), rgba(248,251,255,0.9));
    border: 1px solid rgba(0,188,212,0.1);
    transition: all 0.3s ease;
  }

  .individual-comment:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(0,188,212,0.2);
  }

  .comment-meta {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid rgba(0,188,212,0.2);
  }

  .comment-author {
    color: #0277bd;
    font-weight: 600;
  }

  .comment-date {
    color: #00695c;
    font-size: 0.9rem;
  }

  .comment-text {
    color: #01579b;
    line-height: 1.6;
    margin-bottom: 15px;
  }

  .comment-actions {
    display: flex;
    gap: 10px;
  }

  .action-btn {
    text-transform: none;
    font-weight: 500;
    transition: all 0.3s ease;
  }

  .action-btn:hover {
    transform: scale(1.05);
  }

  .reply-form {
    margin-top: 20px;
    padding: 20px;
    background: linear-gradient(135deg, rgba(0,150,136,0.05), rgba(76,175,80,0.05));
    border-radius: 15px;
    border: 1px solid rgba(0,150,136,0.2);
  }

  .reply-input {
    margin-bottom: 15px;
  }

  .reply-input :deep(.v-field) {
    background: linear-gradient(135deg, rgba(255,255,255,0.9), rgba(240,248,255,0.9));
    border-radius: 12px;
  }

  .reply-submit-btn {
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .reply-submit-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(0,150,136,0.3);
  }

  .reply-item {
    margin-left: 40px;
    margin-top: 15px;
  }

  .reply-comment {
    background: linear-gradient(135deg, rgba(0,150,136,0.05), rgba(76,175,80,0.05));
    border: 1px solid rgba(0,150,136,0.2);
    border-left: 4px solid #009688;
  }
</style>
