import http from 'k6/http';
import {check, sleep} from 'k6';
import {Counter, Trend} from 'k6/metrics';

// 테스트 옵션: 1000 iterations를 100 VU로 실행
export let options = {
    vus: 100,
    iterations: 1000,
};

const TOTAL_TICKETS = 100;
const BASE_URL = 'http://localhost:8080';
const ART_ID = 1;

// 커스텀 메트릭 (export let으로 선언하는 게 k6 공식 스타일)
export let reserveSuccessCounter = new Counter('reserve_success');
export let reserveFailureCounter = new Counter('reserve_failure');
export let purchaseSuccessCounter = new Counter('purchase_success');
export let purchaseFailureCounter = new Counter('purchase_failure');

export let reserveDurationTrend = new Trend('reserve_duration');
export let purchaseDurationTrend = new Trend('purchase_duration');

const headers = {'Content-Type': 'application/json'};

export default function () {
    // 좌석 번호는 1부터 TOTAL_TICKETS 사이의 랜덤 값
    const seatId = Math.floor(Math.random() * TOTAL_TICKETS) + 1;

    // 1. 예약 API 호출
    const reserveRes = http.post(`${BASE_URL}/arts/${ART_ID}/seats/${seatId}/reserve`, null, {headers});
    reserveDurationTrend.add(reserveRes.timings.duration);

    if (reserveRes.status === 200) {
        // 예약 성공 (좌석을 잡았으므로 purchase 호출)
        reserveSuccessCounter.add(1);
        sleep(3);

        // 2. 구매 API 호출
        const purchaseRes = http.post(`${BASE_URL}/arts/${ART_ID}/seats/${seatId}/purchase`, null, {headers});
        purchaseDurationTrend.add(purchaseRes.timings.duration);
        const purchaseCheck = check(purchaseRes, {
            '구매 응답 성공': (res) => res.status === 200 || res.status === 409,
        });
        if (purchaseCheck) {
            purchaseSuccessCounter.add(1);
        } else {
            purchaseFailureCounter.add(1);
        }
    } else {
        // 예약이 200이 아닌 경우인데,
        // 이미 다른 사람이 예약 중인 경우(예: 409 Conflict)라면 failure를 올리지 않음.
        if (reserveRes.status !== 409) {
            reserveFailureCounter.add(1);
        }
    }
}

export function handleSummary(data) {
    // 예약 API 메트릭
    const reserveSuccessCount = data.metrics['reserve_success'].count;
    const reserveFailureCount = data.metrics['reserve_failure'].count;
    const reserveAvg = data.metrics['reserve_duration'].avg;
    const reserveP95 = data.metrics['reserve_duration'].values['p(95)'];

    // 구매 API 메트릭
    const purchaseSuccessCount = data.metrics['purchase_success'].count;
    const purchaseFailureCount = data.metrics['purchase_failure'].count;
    const purchaseAvg = data.metrics['purchase_duration'].avg;
    const purchaseP95 = data.metrics['purchase_duration'].values['p(95)'];

    // 예약/구매된 티켓 수가 전체 티켓 수를 초과하면 오버부킹으로 판단
    const overbooking =
        reserveSuccessCount > TOTAL_TICKETS || purchaseSuccessCount > TOTAL_TICKETS
            ? "Overbooking detected!"
            : "No overbooking";

    const result = `
====== 예약 API Metrics ======
성공 횟수: ${reserveSuccessCount}
실패 횟수: ${reserveFailureCount}
평균 응답 시간: ${reserveAvg.toFixed(2)} ms
95번째 백분위 응답 시간: ${reserveP95.toFixed(2)} ms

====== 구매 API Metrics ======
성공 횟수: ${purchaseSuccessCount}
실패 횟수: ${purchaseFailureCount}
평균 응답 시간: ${purchaseAvg.toFixed(2)} ms
95번째 백분위 응답 시간: ${purchaseP95.toFixed(2)} ms

====== 티켓 상태 ======
전체 티켓 수: ${TOTAL_TICKETS}
예약된 티켓 수: ${reserveSuccessCount}
결제된 티켓 수: ${purchaseSuccessCount}

====== 오버부킹 체크 ======
${overbooking}
`;

    console.log(result);

    return {
        'summary.txt': result,
    };
}