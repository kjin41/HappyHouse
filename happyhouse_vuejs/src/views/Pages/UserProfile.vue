<template>
  <div>
    <my-page></my-page>
    <b-container fluid class="mt--6">
      <b-row>
        <!-- <b-col xl="4" class="order-xl-2 mb-5">
          <user-card></user-card>
        </b-col> -->
        <b-col x="10" class="order-xl-1">
          <edit-profile-form type="view"></edit-profile-form>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
  import EditProfileForm from './UserProfile/EditProfileForm.vue';
  import MyPage from './UserProfile/MyPage.vue';
  import UserCard from './UserProfile/UserCard.vue';

  export default {
    components: {
      EditProfileForm,
      UserCard,
      MyPage
    }
  };
</script>
<style>
</style>
