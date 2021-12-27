<template>
  <footer class="footer px-4">
    <b-row align-v="center" class="justify-content-lg-between">
      <b-col lg="6">
        <div class="copyright text-center text-lg-left text-muted">
          © {{year}} <a href="https://edu.ssafy.com" class="font-weight-bold ml-1" target="_blank">SSAFY</a>
        </div>
      </b-col>
      <b-col lg="6">
        <b-nav align="center" class="nav-footer justify-content-lg-end">
          <b-nav-item href="https://edu.ssafy.com"  target="_blank">
            SSAFT
          </b-nav-item>
          <b-nav-item target="_blank">
           About Us
          </b-nav-item>
          <b-nav-item target="_blank">
            Blog
          </b-nav-item>
          <b-nav-item target="_blank">
            License
          </b-nav-item>
        </b-nav>
      </b-col>
    </b-row>
  </footer>
</template>
<script>
export default {
  data() {
    return {
      year: new Date().getFullYear()
    };
  }
};
</script>
<style></style>
