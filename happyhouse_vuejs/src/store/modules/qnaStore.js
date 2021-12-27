import http from "@/util/http-common";

const qnaStore={
  namespaced: true,
  state:{
    qnas:[],
    qna:{'no':0},
    qnaComments:[],
  },
  getters:{
    qnaList(state){
      return state.qnas;
    },
    
    getQna: (state) => (idx) => {
      let temp=state.qnas.find((qna) => qna.no == idx);
      if(temp)  return temp;
      return state.qna;
    },

    getQnaComments(state){
      return state.qnaComments;
    }
    
  },
  mutations:{
    RESET_LIST(state){
      state.qnas=[];
    },
    SET_LIST(state, data){
      state.qnas=data;
      console.log(state.qnas);
    },
    SET_QNA(state, qna){
      state.qna=qna;
    },
    RESET_QNA(state){
      state.qna={};
    }, 
    SET_QNA_COMMENTS(state, data){
      state.qnaComments=data;
    }
  },  
  actions:{
    getList({commit}, userId){
      http.get(`/qna/${userId}`).then(({data})=>{
        commit("SET_LIST", data);
      })
    },
    getQnaComments(context, payload){
      http.get(payload).then(({ data }) => {
        console.log(data);
        context.commit("SET_QNA_COMMENTS", data);
      });
    },
    resetQna(context){
      context.commit("RESET_QNA");
    },

  },
  // modules:{
  //   QnaList
  // },
  // plugins:[createPersistedState()],
}

export default qnaStore;