import styled from 'styled-components';


export const Wrapper = styled.div`

 display: flex;
 justify-content: space-between;
 flex-direction: column;
 width: 100%;

 border-radius: 20px;
 height: 100%;

 button{
    border-radius: 0 0 20px 20px;
 }

 img{
    max-height: 250px;
    object-fit: corver;
    border-radius: 20px 20px 0 0;
 }

 div{
    font-family: Arial, Helvetica, sans-serif;
    padding: 1rem;
    height: 100%;
 }

 a {
   text-align: center;
  text-decoration: none; /* 링크의 밑줄 제거 */
  color: inherit; /* 링크의 색상 제거 */
 }

 .table{
   border: 1px #a39485 solid;
  font-size: .9em;
  box-shadow: 0 2px 5px rgba(0,0,0,.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;

 }
 thead{
   font-weight: bold;
  color: #fff;
  background: #4B4E5E;
 }

/* .tr{
   height: 100%;
   border: 1px solid #C80850;
} */

.th{
   padding: 1em .5em;
  vertical-align: middle;

}

.td{
   padding: 1em .5em;
  vertical-align: middle;
  text-align: center;
}
  
`;