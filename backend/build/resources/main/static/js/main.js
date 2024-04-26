//1. 부모 div 선택
const footerPolicyDiv = document.querySelector('.footer-bottom-policy');

//2. 반복되는 부분 데이터화
const policyData = [
  {
    href : "https://www.daangn.com/policy/terms",
    content : "이용약관",
    id : 1,
  },
  {
    href : "https://privacy.daangn.com",
    content : "개인정보처리방침",
    id : 2,
  },
  {
    href : "https://www.daangn.com/policy/location",
    content : "위치기반서비스 이용약관",
    id : 3,
  },
  {
    href : "https://www.daangn.com/wv/faqs/3994",
    content : "이용자보호 비전과 계획",
    id : 4,
  },
  {
    href : "https://www.daangn.com/wv/faqs/9010",
    content : "청소년보호정책",
    id : 5,
  },

]

//반복문을 통해 policyData를 요소로 만들기
policyData.forEach(data => {
  //policy-text div와 a태그를 만들어 속성을 추가한다.
  const policyTextDiv = document.createElement('div')
  policyTextDiv.classList.add('policy-text')
  const policyTextA = document.createElement('a')
  policyTextA.classList.add('policy-text')
  policyTextA.classList.add('text-underline')
  policyTextA.href = data.href
  policyTextA.textContent = data.content
  policyTextA.target = "_blank"

  policyTextDiv.append(policyTextA);
  footerPolicyDiv.append(policyTextDiv);
})
